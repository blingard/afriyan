package org.ligot.afriyan.implement;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.ligot.afriyan.Constantes;
import org.ligot.afriyan.service.MinioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Service d'extraction des images base64 inline du contenu Lexical.
 * 
 * Les éditeurs Lexical (Meta) peuvent embarquer les images directement dans le JSON
 * sous forme de data URIs (data:image/png;base64,...). Ces images gonflent massivement
 * la taille du contenu stocké en base de données (un article peut atteindre 13+ MB).
 * 
 * Ce service :
 * 1. Parse le JSON Lexical
 * 2. Parcourt récursivement tous les nœuds
 * 3. Détecte les nœuds "image" dont le "src" commence par "data:image/"
 * 4. Extrait les données base64, les décode en bytes
 * 5. Upload les bytes vers MinIO
 * 6. Remplace le "src" base64 par l'URL MinIO publique
 * 7. Retourne le JSON Lexical modifié (beaucoup plus léger)
 */
@Service
public class LexicalImageExtractorService {

    private static final Logger log = LoggerFactory.getLogger(LexicalImageExtractorService.class);
    private static final String DATA_IMAGE_PREFIX = "data:image/";

    private final MinioService minioService;
    private final ObjectMapper objectMapper;

    public LexicalImageExtractorService(MinioService minioService) {
        this.minioService = minioService;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Traite le contenu Lexical JSON d'un article : extrait les images base64
     * et les remplace par des URLs MinIO.
     *
     * @param contenu   le JSON Lexical brut (champ "contenu" de l'article)
     * @param articleId identifiant de l'article (pour organiser les images dans MinIO)
     * @return le JSON Lexical modifié avec les URLs MinIO à la place des data URIs,
     *         ou le contenu original si aucun traitement n'est nécessaire
     */
    public String extractAndUploadImages(String contenu, Long articleId) {
        if (contenu == null || contenu.isBlank()) {
            return contenu;
        }

        // Vérification rapide : si pas de data:image/, rien à faire
        if (!contenu.contains(DATA_IMAGE_PREFIX)) {
            log.debug("Article {} : aucune image base64 inline détectée", articleId);
            return contenu;
        }

        try {
            JsonNode rootNode = objectMapper.readTree(contenu);
            AtomicInteger imageCount = new AtomicInteger(0);
            
            processNode(rootNode, articleId, imageCount);

            if (imageCount.get() > 0) {
                String result = objectMapper.writeValueAsString(rootNode);
                log.info("Article {} : {} image(s) base64 extraite(s) et uploadée(s) vers MinIO. " +
                         "Taille réduite de {} à {} octets (réduction de {}%).",
                         articleId, imageCount.get(),
                         contenu.length(), result.length(),
                         Math.round((1.0 - (double) result.length() / contenu.length()) * 100));
                return result;
            }

            return contenu;
        } catch (Exception e) {
            log.error("Article {} : erreur lors de l'extraction des images base64. " +
                      "Le contenu original est conservé.", articleId, e);
            return contenu;
        }
    }

    /**
     * Parcourt récursivement un nœud JSON Lexical et traite les images.
     */
    private void processNode(JsonNode node, Long articleId, AtomicInteger imageCount) {
        if (node == null) {
            return;
        }

        // Cas 1 : Nœud image Lexical avec src base64
        if (node.isObject()) {
            JsonNode typeNode = node.get("type");
            JsonNode srcNode = node.get("src");

            if (typeNode != null && "image".equals(typeNode.asText())
                    && srcNode != null && srcNode.asText().startsWith(DATA_IMAGE_PREFIX)) {
                
                String dataUri = srcNode.asText();
                try {
                    String minioUrl = uploadBase64Image(dataUri, articleId, imageCount.incrementAndGet());
                    ((ObjectNode) node).put("src", minioUrl);
                    log.debug("Article {} : image {} remplacée par URL MinIO", articleId, imageCount.get());
                } catch (Exception e) {
                    log.warn("Article {} : échec upload image {} vers MinIO, conservation du base64",
                             articleId, imageCount.get(), e);
                    imageCount.decrementAndGet();
                }
            }

            // Parcourir les enfants de l'objet
            node.fields().forEachRemaining(entry -> {
                processNode(entry.getValue(), articleId, imageCount);
            });
        }

        // Cas 2 : Tableau (ex: "children" dans Lexical)
        if (node.isArray()) {
            for (JsonNode child : node) {
                processNode(child, articleId, imageCount);
            }
        }
    }

    /**
     * Décode un data URI base64 et l'uploade vers MinIO.
     *
     * @param dataUri    ex: "data:image/png;base64,iVBORw0KGgo..."
     * @param articleId  identifiant de l'article
     * @param imageIndex numéro de l'image dans l'article
     * @return URL publique MinIO de l'image uploadée
     */
    private String uploadBase64Image(String dataUri, Long articleId, int imageIndex) throws Exception {
        // Parser le data URI : data:image/<format>;base64,<données>
        // Ex: data:image/png;base64,iVBORw0KGgo...
        int commaIndex = dataUri.indexOf(',');
        if (commaIndex < 0) {
            throw new IllegalArgumentException("Format data URI invalide : pas de virgule séparatrice");
        }

        String metadata = dataUri.substring(0, commaIndex); // "data:image/png;base64"
        String base64Data = dataUri.substring(commaIndex + 1); // "iVBORw0KGgo..."

        // Extraire le type MIME et l'extension
        // metadata = "data:image/png;base64"
        String mimeType = metadata.substring(5, metadata.indexOf(';')); // "image/png"
        String extension = mimeType.substring(mimeType.indexOf('/') + 1);  // "png"

        // Normaliser certaines extensions
        if ("jpeg".equals(extension)) {
            extension = "jpg";
        }
        if ("svg+xml".equals(extension)) {
            extension = "svg";
        }

        // Décoder le base64
        byte[] imageBytes = Base64.getDecoder().decode(base64Data);

        // Construire le chemin dans MinIO : lexical/images/article_<id>/<uuid>.<ext>
        String uniqueName = UUID.randomUUID().toString().substring(0, 12);
        String objectName = Constantes.LEXICALCONTENTIMAGESUBPATH + "/article_" + articleId
                + "/" + uniqueName + "." + extension;

        // Upload vers MinIO
        return minioService.uploadBytes(imageBytes, objectName, mimeType);
    }
}
