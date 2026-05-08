package org.ligot.afriyan.controller;

import jakarta.annotation.security.RolesAllowed;
import org.ligot.afriyan.entities.Articles;
import org.ligot.afriyan.implement.LexicalImageExtractorService;
import org.ligot.afriyan.repository.IArticlesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contrôleur de migration pour traiter les articles existants
 * et externaliser les images base64 inline vers MinIO.
 * 
 * À utiliser UNE SEULE FOIS après le déploiement pour migrer les données existantes.
 * Endpoints protégés par rôle admin.
 */
@RestController
@RequestMapping("api/migration")
public class MigrationController {

    private static final Logger log = LoggerFactory.getLogger(MigrationController.class);

    private final IArticlesRepository articlesRepository;
    private final LexicalImageExtractorService lexicalImageExtractorService;

    public MigrationController(IArticlesRepository articlesRepository,
                               LexicalImageExtractorService lexicalImageExtractorService) {
        this.articlesRepository = articlesRepository;
        this.lexicalImageExtractorService = lexicalImageExtractorService;
    }

    /**
     * Migre un article spécifique : extrait les images base64 du contenu Lexical
     * et les uploade vers MinIO.
     *
     * @param id ID de l'article à migrer
     * @return rapport de migration
     */
    @PostMapping("/articles/extract-images/{id}")
    @RolesAllowed(value = {"UPDATE_ARTICLE"})
    public ResponseEntity<Map<String, Object>> migrateArticle(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        result.put("articleId", id);

        try {
            Articles article = articlesRepository.findById(id).orElse(null);
            if (article == null) {
                result.put("status", "NOT_FOUND");
                result.put("message", "Article non trouvé");
                return ResponseEntity.notFound().build();
            }

            if (article.getContenu() == null || article.getContenu().isBlank()) {
                result.put("status", "SKIPPED");
                result.put("message", "Contenu vide");
                return ResponseEntity.ok(result);
            }

            long sizeBefore = article.getContenu().length();
            
            if (!article.getContenu().contains("data:image/")) {
                result.put("status", "SKIPPED");
                result.put("message", "Aucune image base64 inline détectée");
                result.put("contentSize", sizeBefore);
                return ResponseEntity.ok(result);
            }

            String processedContent = lexicalImageExtractorService.extractAndUploadImages(
                    article.getContenu(), article.getId());
            
            long sizeAfter = processedContent.length();
            
            article.setContenu(processedContent);
            articlesRepository.save(article);

            result.put("status", "MIGRATED");
            result.put("sizeBefore", sizeBefore);
            result.put("sizeAfter", sizeAfter);
            result.put("reduction", Math.round((1.0 - (double) sizeAfter / sizeBefore) * 100) + "%");
            result.put("message", "Images extraites et uploadées vers MinIO avec succès");

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Erreur lors de la migration de l'article {}", id, e);
            result.put("status", "ERROR");
            result.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(result);
        }
    }

    /**
     * Migre TOUS les articles contenant des images base64 inline.
     * Opération potentiellement longue — à exécuter une seule fois.
     *
     * @return rapport de migration global
     */
    @PostMapping("/articles/extract-images-all")
    @RolesAllowed(value = {"UPDATE_ARTICLE"})
    public ResponseEntity<Map<String, Object>> migrateAllArticles() {
        Map<String, Object> result = new HashMap<>();
        int migrated = 0;
        int skipped = 0;
        int errors = 0;
        long totalSizeBefore = 0;
        long totalSizeAfter = 0;

        try {
            List<Articles> allArticles = articlesRepository.findAll();
            result.put("totalArticles", allArticles.size());

            for (Articles article : allArticles) {
                try {
                    if (article.getContenu() == null || article.getContenu().isBlank()
                            || !article.getContenu().contains("data:image/")) {
                        skipped++;
                        continue;
                    }

                    long sizeBefore = article.getContenu().length();
                    totalSizeBefore += sizeBefore;

                    String processedContent = lexicalImageExtractorService.extractAndUploadImages(
                            article.getContenu(), article.getId());

                    long sizeAfter = processedContent.length();
                    totalSizeAfter += sizeAfter;

                    article.setContenu(processedContent);
                    articlesRepository.save(article);
                    migrated++;

                    log.info("Article {} migré : {} → {} octets",
                             article.getId(), sizeBefore, sizeAfter);
                } catch (Exception e) {
                    errors++;
                    log.error("Erreur migration article {}", article.getId(), e);
                }
            }

            result.put("status", "COMPLETED");
            result.put("migrated", migrated);
            result.put("skipped", skipped);
            result.put("errors", errors);
            result.put("totalSizeBefore", totalSizeBefore);
            result.put("totalSizeAfter", totalSizeAfter);
            if (totalSizeBefore > 0) {
                result.put("totalReduction",
                        Math.round((1.0 - (double) totalSizeAfter / totalSizeBefore) * 100) + "%");
            }

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Erreur lors de la migration globale", e);
            result.put("status", "ERROR");
            result.put("message", e.getMessage());
            result.put("migrated", migrated);
            result.put("errors", errors);
            return ResponseEntity.internalServerError().body(result);
        }
    }

}
