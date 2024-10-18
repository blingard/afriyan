package org.ligot.afriyan.implement;

import org.ligot.afriyan.config.file.FileStorageProperties;
import org.ligot.afriyan.entities.Mediatech;
import org.ligot.afriyan.exception.FileStorageException;
import org.ligot.afriyan.exception.MyFileNotFoundException;
import org.ligot.afriyan.repository.IMediatechRepository;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Base64;

@Service
public class FileStorageService {
    private final FileStorageProperties fileStorageProperties;
    private final IMediatechRepository repository;

    public FileStorageService(FileStorageProperties fileStorageProperties, IMediatechRepository repository) {
        this.fileStorageProperties = fileStorageProperties;
        this.repository = repository;
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


    public void storeFileStory(MultipartFile file, String text) throws Exception {
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
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/file/downloadFile/")
                    .path(name)
                    .toUriString();
            repository.save(new Mediatech(null, fileDownloadUri, text, false, file.getContentType(), file.getSize()));
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

    public String storeParagraphFileImage(MultipartFile file, String fileSubPath) throws Exception {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            String[] a = fileName.trim().split("\\.");
            String name = String.valueOf(System.currentTimeMillis())+'.'+a[1];
            Path targetLocation = Paths.get(createDirectory(fileSubPath))
                    .toAbsolutePath().normalize().resolve(name);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            String[] elements = name.split("\\.");
            name=name+":"+elements[1];
            return name;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) throws MalformedURLException {
        try {
            Path filePath = Paths.get("uploads/").resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    private byte[] getFile(String filePath) {
        try{

            String file = this.fileStorageProperties.getUploadDir().trim()+filePath;
            System.err.println(file);
            Path encryptedImagePath = Paths.get(file);
            return Files.readAllBytes(encryptedImagePath);
        }catch (Exception ex){
            return null;
        }
    }

    public String convertImageToBase64(String imagePath) {
        try {
            byte[] fileContent = getFile(imagePath);
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (Exception e) {
            return null;
        }
    }
}
