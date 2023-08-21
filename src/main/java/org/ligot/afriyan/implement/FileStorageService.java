package org.ligot.afriyan.implement;

import org.ligot.afriyan.config.file.FileStorageProperties;
import org.ligot.afriyan.exception.FileStorageException;
import org.ligot.afriyan.exception.MyFileNotFoundException;
import org.ligot.afriyan.repository.ICarrouselRepository;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private final FileStorageProperties fileStorageProperties;

    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageProperties = fileStorageProperties;
    }

    String createDirectory(String path) throws Exception{
        if(Files.exists(Paths.get(this.fileStorageProperties.getUploadDir().trim()+path.trim())))
            return this.fileStorageProperties.getUploadDir().trim()+path.trim();
        Files.createDirectories(
                Paths
                        .get(
                                this.fileStorageProperties
                                        .getUploadDir()
                                        .trim()+path.trim())
                        .toAbsolutePath()
                        .normalize()
        );
        return this.fileStorageProperties.getUploadDir().trim()+path.trim();

    }

    public String storeFile(MultipartFile file) throws Exception {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            String[] a = fileName.trim().split("\\.");
            String name = String.valueOf(System.currentTimeMillis())+'.'+a[1];
            Path targetLocation = Paths.get(createDirectory(""))
                    .toAbsolutePath().normalize().resolve(name);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return name;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public String storeFileCarrousel(MultipartFile file) throws Exception {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            String[] a = fileName.trim().split("\\.");
            String name = String.valueOf(System.currentTimeMillis())+'.'+a[1];
            Path targetLocation = Paths.get(createDirectory("/carrousel"))
                    .toAbsolutePath().normalize().resolve(name);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return name;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) throws MalformedURLException {
        System.err.println("hgsdvnmasvndmbvmansvbdmnvnbsdvmndvbmnfvbnsdmb");
        Path path = null;
        return new UrlResource(path.toUri());
        /*try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }*/
    }
}
