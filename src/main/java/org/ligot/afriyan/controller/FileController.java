package org.ligot.afriyan.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.ligot.afriyan.Dto.CarrouselDTO;
import org.ligot.afriyan.Dto.MediatechRequest;
import org.ligot.afriyan.Dto.UploadFileResponse;
import org.ligot.afriyan.entities.Carrousel;
import org.ligot.afriyan.implement.FileStorageService;
import org.ligot.afriyan.repository.ICarrouselRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("api/file")
public class FileController {

    private final FileStorageService fileStorageService;
    private final ICarrouselRepository repository;

    public FileController(FileStorageService fileStorageService, ICarrouselRepository repository) {
        this.fileStorageService = fileStorageService;
        this.repository = repository;
    }

    // -------------------------------------------------------------------------
    // Upload
    // -------------------------------------------------------------------------

    /**
     * Upload générique d'un fichier vers MinIO.
     * Retourne l'objectName MinIO + une URL pré-signée valable 24 h.
     */
    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        String objectName = fileStorageService.storeFile(file);
        String presignedUrl = fileStorageService.getPresignedUrl(objectName, 24);
        return new UploadFileResponse(objectName, presignedUrl, file.getContentType(), file.getSize());
    }

    /**
     * Upload d'un fichier médiathèque + texte descriptif.
     */
    @PostMapping("/uploadFile/mediatech")
    public ResponseEntity<Void> uploadMediatech(
            @RequestParam("file") MultipartFile file,
            @RequestParam("text") String text) throws Exception {
        fileStorageService.storeFileStory(file, text);
        return ResponseEntity.ok().build();
    }

    /**
     * Upload d'une image de carrousel.
     */
    @PostMapping("/uploadCarrousel")
    public UploadFileResponse uploadCarrousel(
            @RequestParam("file") MultipartFile file,
            @RequestParam("titre") String titre,
            @RequestParam("description") String description) throws Exception {

        String objectName = fileStorageService.storeFileCarrousel(file);
        String presignedUrl = fileStorageService.getPresignedUrl(objectName, 24);

        repository.save(new Carrousel(null, objectName, titre, description, false));

        return new UploadFileResponse(objectName, presignedUrl, file.getContentType(), file.getSize());
    }

    // -------------------------------------------------------------------------
    // Lecture / Visualisation
    // -------------------------------------------------------------------------

    /**
     * Stream un fichier depuis MinIO directement au navigateur (visualisation
     * inline).
     * Exemple : GET /api/file/view/uploads/1708000000000.pdf
     */
    @GetMapping("/view/{objectName:.+}")
    public ResponseEntity<byte[]> viewFile(
            @PathVariable String objectName,
            HttpServletRequest request) throws Exception {

        try (InputStream is = fileStorageService.getFileInputStream(objectName)) {
            byte[] content = is.readAllBytes();

            // Déterminer le Content-Type via la requête ou fallback générique
            String contentType = request.getServletContext().getMimeType(objectName);
            if (contentType == null || contentType.isBlank()) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + getFilename(objectName) + "\"")
                    .body(content);
        }
    }

    /**
     * Retourne une URL pré-signée MinIO pour accès temporaire direct au fichier.
     * Exemple : GET /api/file/url/uploads/1708000000000.pdf?hours=48
     */
    @GetMapping("/url/{objectName:.+}")
    public ResponseEntity<Map<String, String>> getPresignedUrl(
            @PathVariable String objectName,
            @RequestParam(name = "hours", defaultValue = "24") long hours) throws Exception {

        String url = fileStorageService.getPresignedUrl(objectName, hours);
        return ResponseEntity.ok(Map.of("url", url, "objectName", objectName));
    }

    /**
     * Supprime un fichier depuis MinIO.
     * Exemple : DELETE /api/file/uploads/1708000000000.pdf
     */
    @DeleteMapping("/{objectName:.+}")
    public ResponseEntity<Void> deleteFile(@PathVariable String objectName) throws Exception {
        fileStorageService.deleteFile(objectName);
        return ResponseEntity.noContent().build();
    }

    // -------------------------------------------------------------------------
    // Utilitaires
    // -------------------------------------------------------------------------

    private String getFilename(String objectName) {
        if (objectName == null)
            return "file";
        int idx = objectName.lastIndexOf('/');
        return idx >= 0 ? objectName.substring(idx + 1) : objectName;
    }
}
