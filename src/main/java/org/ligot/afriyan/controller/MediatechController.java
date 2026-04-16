package org.ligot.afriyan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.ligot.afriyan.Dto.*;
import org.ligot.afriyan.entities.UserConnect;
import org.ligot.afriyan.repository.IUserConnect;
import org.ligot.afriyan.service.IMediatech;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class MediatechController {
    private final IMediatech mediatech;
    private final IUserConnect iUserConnect;

    public MediatechController(IMediatech mediatech, IUserConnect iUserConnect) {
        this.mediatech = mediatech;
        this.iUserConnect = iUserConnect;
    }



    @GetMapping("api/mediatech/active")
    public List<MediatechDTO> findAllActive(){
        return mediatech.findAllActive();
    }
    @GetMapping
    public List<MediatechDTO> findAll(){
        return mediatech.findAll();
    }

    @PutMapping("api/mediatech/active/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public void active(@PathVariable Long id) throws Exception {
        mediatech.activeOrDesable(id);
    }


    @PostMapping("api/gallery/save")
    @RolesAllowed(value = {"CREATE_SLIDER"})
    public void saveMediatechFile(
            @RequestParam(name = "file",required = false) MultipartFile file,
            @RequestParam( "jsonData") String jsonData) throws Exception {
        GalleryRequest galleryRequest = new ObjectMapper().readValue(jsonData, GalleryRequest.class);
        mediatech.save(file, galleryRequest);
    }

    @PutMapping(value = "api/gallery/update/{id}")
    @RolesAllowed(value = {"UPDATE_SLIDER"})
    void updateSlide(@RequestBody @Valid GalleryRequestUpdate galleryRequestUpdate, @PathVariable UUID id) throws Exception {
        mediatech.update(galleryRequestUpdate, id);
    }

    @PutMapping(value = "api/gallery/active/{id}")
    @RolesAllowed(value = {"UPDATE_SLIDER"})
    void updateSlide(@PathVariable UUID id) {
        mediatech.activeOrDesable(id);
    }

    @GetMapping("api/gallery/active")
    @RolesAllowed(value = {"GET_SLIDER"})
    public PageDTO<GalleryDTO> findAllGallery(@PageableDefault(size = 10) Pageable pageable){
        return mediatech.findAllActiveGallery(pageable);
    }

    @GetMapping("public/gallery/active")
    public List<GalleryDTO> findAllActiveGallery(){
        return mediatech.findAllActiveGallery();
    }

    @GetMapping("api/gallery/{id}")
    public GalleryDTO findAllActiveGallery(@PathVariable UUID id){
        return mediatech.findById(id);
    }
}
