package org.ligot.afriyan.implement;

import lombok.extern.slf4j.Slf4j;
import org.ligot.afriyan.entities.Mediatech;
import org.ligot.afriyan.exception.FileStorageException;
import org.ligot.afriyan.exception.MyFileNotFoundException;
import org.ligot.afriyan.repository.IMediatechRepository;
import org.ligot.afriyan.service.MinioService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.Duration;
import java.util.Base64;

/**
 * Service de stockage de fichiers utilisant MinIO comme backend.
 * Remplace l'ancienne implémentation basée sur le système de fichiers local.
 */
@Slf4j
@Service
public class FileStorageService {

    private final IMediatechRepository repository;
    private final MinioService minioService;

    // Préfixes MinIO par type de fichier
    private static final String PREFIX_CARROUSEL = "carrousel/";
    private static final String PREFIX_DEFAULT = "uploads/";

    public FileStorageService(IMediatechRepository repository, MinioService minioService) {
        this.repository = repository;
        this.minioService = minioService;
    }

    // -------------------------------------------------------------------------
    // Upload
    // -------------------------------------------------------------------------

    /**
     * Upload un fichier générique vers MinIO.
     *
     * @param file fichier à uploader
     * @return URL publique directe du fichier (ex:
     *         http://minio:9000/bucket/uploads/fichier.jpg)
     */
    public String storeFile(MultipartFile file) throws Exception {
        String fileName = validateAndGetName(file);
        String objectName = PREFIX_DEFAULT + fileName;
        // log.info("Stockage du fichier dans MinIO : {}", objectName);
        return minioService.uploadFile(file, objectName);
    }

    /**
     * Upload un fichier de médiathèque et sauvegarde les métadonnées.
     *
     * @param file fichier à uploader
     * @param text description associée
     */
    public void storeFileStory(MultipartFile file, String text) throws Exception {
        String fileUrl = storeFile(file);
        // On stocke l'URL publique directe – utilisable immédiatement dans un
        // navigateur
        repository.save(new Mediatech(null, fileUrl, text, false, file.getContentType(), file.getSize()));
        // log.info("Mediatech sauvegardé avec URL : {}", fileUrl);
    }

    /**
     * Upload un fichier vers le préfixe "carrousel/" dans MinIO.
     *
     * @param file fichier à uploader
     * @return URL publique directe du fichier carrousel
     */
    public String storeFileCarrousel(MultipartFile file) throws Exception {
        String fileName = validateAndGetName(file);
        String objectName = PREFIX_CARROUSEL + fileName;
        // log.info("Stockage du carrousel dans MinIO : {}", objectName);
        return minioService.uploadFile(file, objectName);
    }

    /**
     * Upload une image de paragraphe vers un sous-préfixe MinIO.
     *
     * @param file        fichier image
     * @param fileSubPath préfixe/sous-répertoire dans le bucket
     * @return URL publique directe du fichier uploadé
     */
    public String storeParagraphFileImage(MultipartFile file, String fileSubPath) throws Exception {
        String fileName = validateAndGetName(file);
        String prefix = fileSubPath != null && !fileSubPath.isBlank()
                ? fileSubPath.replaceAll("^/", "") + "/"
                : PREFIX_DEFAULT;
        String objectName = prefix + fileName;
        // log.info("Stockage de l'image paragraphe dans MinIO : {}", objectName);
        return minioService.uploadFile(file, objectName);
    }

    // -------------------------------------------------------------------------
    // Lecture / Visualisation
    // -------------------------------------------------------------------------

    /**
     * Récupère un flux d'entrée pour un fichier stocké dans MinIO.
     * À utiliser dans le controller pour streamer la réponse vers le client.
     *
     * @param objectName chemin de l'objet dans MinIO
     * @return InputStream du fichier
     */
    public InputStream getFileInputStream(String objectName) throws Exception {
        if (objectName == null || objectName.isBlank()) {
            throw new MyFileNotFoundException("Nom de fichier manquant");
        }
        try {
            // log.info("Récupération du flux MinIO pour : {}", objectName);
            return minioService.downloadFile(objectName);
        } catch (Exception e) {
            throw new MyFileNotFoundException("Fichier introuvable dans MinIO : " + objectName, e);
        }
    }

    /**
     * Génère une URL pré-signée MinIO donnant un accès temporaire à un fichier.
     *
     * @param objectName    chemin de l'objet dans MinIO
     * @param durationHours durée de validité en heures (0 → défaut 24 h)
     * @return URL pré-signée
     */
    public String getPresignedUrl(String objectName, long durationHours) throws Exception {
        Duration expiry = durationHours > 0 ? Duration.ofHours(durationHours) : Duration.ofHours(24);
        // log.info("Génération URL pré-signée pour {} (expiry: {}h)", objectName,
        // expiry.toHours());
        return minioService.getFileUrl(objectName, expiry);
    }

    /**
     * Convertit un fichier MinIO en chaîne Base64.
     * Pratique pour les réponses JSON embarquant des images petites.
     *
     * @param objectName chemin de l'objet dans MinIO
     * @return chaîne Base64 ou null en cas d'erreur
     */
    public String convertImageToBase64(String objectName) {
        try (InputStream is = minioService.downloadFile(objectName)) {
            byte[] bytes = is.readAllBytes();
            // log.info("Conversion Base64 pour : {}", objectName);
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            // log.warn("Impossible de convertir en Base64 : {}", objectName, e);
            return null;
        }
    }

    // -------------------------------------------------------------------------
    // Suppression
    // -------------------------------------------------------------------------

    /**
     * Supprime un fichier du bucket MinIO.
     *
     * @param objectName chemin de l'objet à supprimer
     */
    public void deleteFile(String objectName) throws Exception {
        // log.info("Suppression du fichier MinIO : {}", objectName);
        minioService.deleteFile(objectName);
    }

    // -------------------------------------------------------------------------
    // Utilitaires privés
    // -------------------------------------------------------------------------

    /**
     * Valide le fichier et génère un nom unique horodaté.
     */
    private String validateAndGetName(MultipartFile file) throws FileStorageException {
        String original = StringUtils.cleanPath(
                file.getOriginalFilename() != null ? file.getOriginalFilename() : "file");
        if (original.contains("..")) {
            throw new FileStorageException("Nom de fichier invalide : " + original);
        }
        String ext = getExtension(original);
        return System.currentTimeMillis() + (ext.isBlank() ? "" : "." + ext);
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains("."))
            return "";
        return filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
    }
}
