package org.ligot.afriyan.test;

import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.implement.UtilsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import org.ligot.afriyan.service.MinioService;
import org.springframework.http.MediaType;

// DocumentEditorController.java — génère l'URL pour React
@RestController
@RequestMapping("public/documents")
public class DocumentEditorController {

    @Value("${collabora.url:http://localhost:9980}")
    private String collaboraUrl;

    @Value("${app.public-url:http://localhost:8080}")
    private String appPublicUrl;

    private final WopiTokenService tokenService;
    private final CollaboraDiscoveryService discoveryService;
    private final DocumentRepository documentRepository;
    private final MinioService minioService;
    private final UtilsService utilsService;

    public DocumentEditorController(WopiTokenService tokenService, CollaboraDiscoveryService discoveryService,
            DocumentRepository documentRepository, MinioService minioService, UtilsService utilsService) {
        this.tokenService = tokenService;
        this.discoveryService = discoveryService;
        this.documentRepository = documentRepository;
        this.minioService = minioService;
        this.utilsService = utilsService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> uploadDocument(@RequestParam("file") MultipartFile file)
            throws Exception {
        String fileId = UUID.randomUUID().toString();
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null)
            originalFilename = "document";
        String extension = "";
        if (originalFilename.contains(".")) {
            //extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        }

        minioService.saveFileTest(fileId+"."+extension, file.getBytes());
        Utilisateur utilisateur = utilsService.getUser();

        String username = utilisateur.getEmail();
        Document doc = new Document();
        doc.setId(fileId);
        doc.setNom(originalFilename);
        doc.setExtension(extension);
        doc.setTaille(file.getSize());
        doc.setVersion(1);
        doc.setProprietaireId(username);

        documentRepository.save(doc);

        return ResponseEntity.ok(Map.of("fileId", fileId));
    }

    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments() {
        List<Document> documents = documentRepository.findAll();
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<Document> getDocument(@PathVariable String fileId) {
        Document doc = documentRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("Document non trouvé: " + fileId));
        return ResponseEntity.ok(doc);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Map<String, String>> deleteDocument(@PathVariable String fileId) {
        try {
            Document doc = documentRepository.findById(fileId)
                    .orElseThrow(() -> new RuntimeException("Document non trouvé: " + fileId));
            
            // Supprimer le fichier de MinIO
            String minioKey = fileId + "." + doc.getExtension();
            try {
                minioService.deleteFile(minioKey);
            } catch (Exception e) {
                // Log mais ne bloque pas la suppression en BD
                System.err.println("Erreur suppression MinIO pour " + minioKey + ": " + e.getMessage());
            }
            
            // Supprimer de la BD
            documentRepository.deleteById(fileId);
            
            return ResponseEntity.ok(Map.of("status", "deleted", "fileId", fileId));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

    @PutMapping("/{fileId}")
    public ResponseEntity<Map<String, String>> updateDocument(
            @PathVariable String fileId,
            @RequestBody Map<String, String> body) {
        Document doc = documentRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("Document non trouvé: " + fileId));
        
        if (body.containsKey("nom")) {
            doc.setNom(body.get("nom"));
        }
        documentRepository.save(doc);
        
        return ResponseEntity.ok(Map.of("status", "updated", "fileId", fileId));
    }

    /**
     * Endpoint de diagnostic : vérifie la cohérence BDD <-> MinIO pour un document.
     * Appel : GET /public/documents/{fileId}/debug
     */
    @GetMapping("/{fileId}/debug")
    public ResponseEntity<Map<String, Object>> debugDocument(@PathVariable String fileId) {
        Document doc = documentRepository.findById(fileId).orElse(null);
        if (doc == null) {
            return ResponseEntity.ok(Map.of(
                    "fileId", fileId,
                    "status", "NOT_FOUND_IN_DB",
                    "message", "Ce fileId n'existe pas dans la table wopi_document"
            ));
        }

        String minioKey = fileId + "." + doc.getExtension();
        boolean existsInMinio = false;
        long size = -1;
        String minioError = null;
        try {
            existsInMinio = minioService.fileExists(minioKey);
            if (existsInMinio) {
                size = minioService.getFileSizeTest(minioKey);
            }
        } catch (Exception e) {
            minioError = e.getMessage();
        }

        return ResponseEntity.ok(Map.of(
                "fileId", fileId,
                "nom", doc.getNom(),
                "extension", doc.getExtension(),
                "tailleDB", doc.getTaille(),
                "minioKey", minioKey,
                "existsInMinio", existsInMinio,
                "tailleMinIO", size,
                "minioError", minioError != null ? minioError : "none"
        ));
    }

    @GetMapping("/{fileId}/editor-url")
    public ResponseEntity<Map<String, String>> getEditorUrl(
            @PathVariable String fileId,
            @RequestParam(defaultValue = "false") boolean readOnly) {

        Utilisateur utilisateur = utilsService.getUser();
        // Générer un token WOPI (expire en 10 min)
        String token = tokenService.generate(fileId, utilisateur.getUuid(), utilisateur.getEmail(), !readOnly);

        // URL WOPI que Collabora va appeler pour récupérer le fichier
        String wopiSrc = appPublicUrl + "/public/wopi/files/" + fileId;

        // Trouver l'éditeur Collabora selon l'extension du fichier
        Document doc = documentRepository.findById(fileId).orElseThrow();
        
        String editorUrl = discoveryService.getEditorUrl(
                collaboraUrl, doc.getExtension(), readOnly ? "view" : "edit");

        String separator = editorUrl.contains("?") ? (editorUrl.endsWith("?") || editorUrl.endsWith("&") ? "" : "&") : "?";
        String fullUrl = editorUrl
                + separator
                + "WOPISrc=" + URLEncoder.encode(wopiSrc, StandardCharsets.UTF_8)
                + "&access_token=" + token
                + "&lang=fr";

        return ResponseEntity.ok(Map.of(
                "url", fullUrl,
                "token", token));
    }
}