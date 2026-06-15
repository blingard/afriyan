package org.ligot.afriyan.implement;

import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.ligot.afriyan.config.MinioProperties;
import org.ligot.afriyan.service.MinioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of MinIO service for object storage operations
 */
// @Slf4j
@Service
@ConditionalOnProperty(prefix = "minio", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MinioServiceImpl implements MinioService {
    private static final Logger log = LoggerFactory.getLogger(MinioServiceImpl.class);

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    /**
     * Allowed file extensions for security
     */
    private static final List<String> ALLOWED_EXTENSIONS = List.of(
            "jpg", "jpeg", "png", "gif", "pdf", "doc", "docx",
            "xls", "xlsx", "ppt", "pptx", "txt", "csv", "mp4",
            "avi", "mp3", "zip", "rar");

    public MinioServiceImpl(MinioClient minioClient, MinioProperties minioProperties) {
        this.minioClient = minioClient;
        this.minioProperties = minioProperties;
    }

    @Override
    public String uploadFile(MultipartFile file, String objectName) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be null or empty");
        }

        // Generate unique object name if not provided
        if (objectName == null || objectName.isBlank()) {
            String originalFilename = file.getOriginalFilename();
            String extension = getFileExtension(originalFilename);
            objectName = generateUniqueFileName(extension);
        }

        // Validate file extension
        String extension = getFileExtension(objectName);
        if (!isAllowedExtension(extension)) {
            throw new IllegalArgumentException("File type not allowed: " + extension);
        }

        // Validate MIME type
        String contentType = file.getContentType();
        if (contentType == null || contentType.isBlank()) {
            contentType = "application/octet-stream";
        }

        // log.info("Uploading file to MinIO: {} (size: {} bytes, type: {})",objectName,
        // file.getSize(), contentType);

        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .object(objectName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(contentType)
                            .build());

            // log.info("File uploaded successfully: {}", objectName);
            // Retourne l'URL publique directe : {endpoint}/{bucket}/{objectName}
            String endpoint = minioProperties.getEndpoint();
            // Supprimer le slash final de l'endpoint s'il y en a un
            if (endpoint.endsWith("/")) {
                endpoint = endpoint.substring(0, endpoint.length() - 1);
            }
            return minioProperties.getFileurl() + "/" + minioProperties.getBucketName() + "/" + objectName;
        } catch (Exception e) {
            // log.error("Error uploading file to MinIO: {}", objectName, e);
            throw new RuntimeException("Failed to upload file: " + objectName, e);
        }
    }

    @Override
    public InputStream downloadFile(String objectName) throws Exception {
        if (objectName == null || objectName.isBlank()) {
            throw new IllegalArgumentException("Object name cannot be null or empty");
        }

        // log.info("Downloading file from MinIO: {}", objectName);

        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .object(objectName)
                            .build());
        } catch (Exception e) {
            // log.error("Error downloading file from MinIO: {}", objectName, e);
            throw new RuntimeException("Failed to download file: " + objectName, e);
        }
    }

    @Override
    public void deleteFile(String objectName) throws Exception {
        if (objectName == null || objectName.isBlank()) {
            throw new IllegalArgumentException("Object name cannot be null or empty");
        }

        // log.info("Deleting file from MinIO: {}", objectName);

        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .object(objectName)
                            .build());

            // log.info("File deleted successfully: {}", objectName);
        } catch (Exception e) {
            // log.error("Error deleting file from MinIO: {}", objectName, e);
            throw new RuntimeException("Failed to delete file: " + objectName, e);
        }
    }

    @Override
    public String getFileUrl(String objectName, Duration expiry) throws Exception {
        if (objectName == null || objectName.isBlank()) {
            throw new IllegalArgumentException("Object name cannot be null or empty");
        }

        // Default expiry: 7 days
        if (expiry == null) {
            expiry = Duration.ofDays(7);
        }

        // log.info("Generating pre-signed URL for: {} (expiry: {})", objectName,
        // expiry);

        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(minioProperties.getBucketName())
                            .object(objectName)
                            .expiry((int) expiry.getSeconds(), TimeUnit.SECONDS)
                            .build());
        } catch (Exception e) {
            // log.error("Error generating pre-signed URL for: {}", objectName, e);
            throw new RuntimeException("Failed to generate URL for: " + objectName, e);
        }
    }

    @Override
    public List<String> listFiles(String prefix) throws Exception {
        // log.info("Listing files in MinIO with prefix: {}", prefix);

        List<String> fileNames = new ArrayList<>();

        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .prefix(prefix)
                            .recursive(true)
                            .build());

            for (Result<Item> result : results) {
                Item item = result.get();
                fileNames.add(item.objectName());
            }

            // log.info("Found {} files with prefix: {}", fileNames.size(), prefix);
            return fileNames;
        } catch (Exception e) {
            // log.error("Error listing files in MinIO", e);
            throw new RuntimeException("Failed to list files", e);
        }
    }

    @Override
    public boolean fileExists(String objectName) throws Exception {
        if (objectName == null || objectName.isBlank()) {
            return false;
        }

        try {
            minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .object(objectName)
                            .build());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public long getFileSize(String objectName) throws Exception {
        if (objectName == null || objectName.isBlank()) {
            throw new IllegalArgumentException("Object name cannot be null or empty");
        }

        try {
            StatObjectResponse stat = minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .object(objectName)
                            .build());
            return stat.size();
        } catch (Exception e) {
            // log.error("Error getting file size: {}", objectName, e);
            throw new RuntimeException("Failed to get file size: " + objectName, e);
        }
    }

    @Override
    public String uploadBytes(byte[] data, String objectName, String contentType) throws Exception {
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("Data cannot be null or empty");
        }
        if (objectName == null || objectName.isBlank()) {
            throw new IllegalArgumentException("Object name cannot be null or empty");
        }
        if (contentType == null || contentType.isBlank()) {
            contentType = "application/octet-stream";
        }

        try (InputStream inputStream = new java.io.ByteArrayInputStream(data)) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .object(objectName)
                            .stream(inputStream, data.length, -1)
                            .contentType(contentType)
                            .build());

            String endpoint = minioProperties.getEndpoint();
            if (endpoint.endsWith("/")) {
                endpoint = endpoint.substring(0, endpoint.length() - 1);
            }
            return minioProperties.getFileurl() + "/" + minioProperties.getBucketName() + "/" + objectName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload bytes: " + objectName, e);
        }
    }

    /**
     * Generate a unique file name with timestamp and UUID
     */
    private String generateUniqueFileName(String extension) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return String.format("%s_%s.%s", timestamp, uuid, extension);
    }

    /**
     * Extract file extension from filename
     */
    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }

    /**
     * Check if file extension is allowed
     */
    private boolean isAllowedExtension(String extension) {
        return ALLOWED_EXTENSIONS.contains(extension.toLowerCase());
    }

    @Override
    public byte[] getFileTest(String fileId) throws Exception {
        InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(minioProperties.getBucketName())
                        .object(fileId)
                        .build());
        return stream.readAllBytes();
    }

    @Override
    public void saveFileTest(String fileId, byte[] content) throws Exception {
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(minioProperties.getBucketName())
                        .object(fileId)
                        .stream(new ByteArrayInputStream(content), content.length, -1)
                        .contentType("application/octet-stream")
                        .build());
    }

    @Override
    public long getFileSizeTest(String fileId) throws Exception {
        StatObjectResponse stat = minioClient.statObject(
                StatObjectArgs.builder().bucket(minioProperties.getBucketName()).object(fileId).build());
        return stat.size();
    }
}
