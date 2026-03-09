package org.ligot.afriyan.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for MinIO client
 */
@Configuration
@ConditionalOnProperty(prefix = "minio", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MinioConfig {

    private final MinioProperties minioProperties;

    public MinioConfig(MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
    }

    /**
     * Creates and configures MinIO client bean
     *
     * @return configured MinioClient instance
     */
    @Bean
    public MinioClient minioClient() {
        //log.info("Initializing MinIO client with endpoint: {}", minioProperties.getEndpoint());

        MinioClient minioClient = MinioClient.builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();

        // Create default bucket if it doesn't exist
        try {
            String bucketName = minioProperties.getBucketName();
            boolean bucketExists = minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(bucketName)
                            .build());

            if (!bucketExists) {
                //log.info("Creating bucket: {}", bucketName);
                minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(bucketName)
                                .build());
                //log.info("Bucket '{}' created successfully", bucketName);

                // Set public download policy
                setPublicDownloadPolicy(minioClient, bucketName);
            } else {
                //log.info("Bucket '{}' already exists", bucketName);
            }
        } catch (Exception e) {
            //log.error("Error initializing MinIO bucket", e);
            throw new RuntimeException("Failed to initialize MinIO bucket", e);
        }

        return minioClient;
    }

    /**
     * Set public download policy for the bucket
     * Allows public read access (GetObject) to all objects in the bucket
     */
    private void setPublicDownloadPolicy(MinioClient minioClient, String bucketName) {
        try {
            String policy = """
                    {
                        "Version": "2012-10-17",
                        "Statement": [
                            {
                                "Effect": "Allow",
                                "Principal": {"AWS": "*"},
                                "Action": ["s3:GetObject"],
                                "Resource": ["arn:aws:s3:::%s/*"]
                            }
                        ]
                    }
                    """.formatted(bucketName);

            minioClient.setBucketPolicy(
                    SetBucketPolicyArgs.builder()
                            .bucket(bucketName)
                            .config(policy)
                            .build());

            //log.info("Public download policy set for bucket: {}", bucketName);
        } catch (Exception e) {
            //log.warn("Failed to set public policy for bucket: {}. Files will be private.", bucketName, e);
        }
    }
}
