package org.ligot.afriyan.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.ligot.afriyan.Dto.CarrouselDTO;
import org.ligot.afriyan.Dto.UploadFileResponse;
import org.ligot.afriyan.entities.Carrousel;
import org.ligot.afriyan.implement.FileStorageService;
import org.ligot.afriyan.repository.ICarrouselRepository;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;

@RestController
@RequestMapping("api/file")
public class FileController {

    private final FileStorageService fileStorageService;
    private final ICarrouselRepository repository;

    public FileController(FileStorageService fileStorageService, ICarrouselRepository repository) {
        this.fileStorageService = fileStorageService;
        this.repository = repository;
    }

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/file/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadCarrousel/")
    public UploadFileResponse uploadCarrousel(@RequestParam("file") MultipartFile file, @RequestBody CarrouselDTO carrousel) throws Exception {
        String fileName = fileStorageService.storeFileCarrousel(file);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/file/downloadFile/")
                .path(fileName)
                .toUriString();
        repository.save(new Carrousel(null, fileDownloadUri, carrousel.getTitre(), carrousel.getDescription(), false));

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws MalformedURLException {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            //logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
