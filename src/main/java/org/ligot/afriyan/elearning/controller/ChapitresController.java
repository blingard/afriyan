package org.ligot.afriyan.elearning.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import org.ligot.afriyan.elearning.dto.ChapitresDTO;
import org.ligot.afriyan.elearning.dto.ParagraphsDTO;
import org.ligot.afriyan.elearning.service.ChapterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Tag(name = "Chapitres", description = "Gestion des Chapitres")
public class ChapitresController {
    private final ChapterService services;

    public ChapitresController(ChapterService services) {
        this.services = services;
    }

    @GetMapping("api/chapter/admin/{id}")
    @RolesAllowed(value = {"GET_CHAPTER_ADMIN"})
    public ResponseEntity<ChapitresDTO> findChapterAdmin(@PathVariable("id") Long id)throws Exception{
        return new ResponseEntity<>(services.getByIdAdmin(id), HttpStatus.OK);
    }

    @GetMapping("api/chapter/admin/chap/{id}")
    @RolesAllowed(value = {"GET_PARAGRAPH_ADMIN"})
    public ResponseEntity<List<ParagraphsDTO>> findChapterAdminP(@PathVariable("id") Long id)throws Exception{
        return new ResponseEntity<>(services.getByIdAdminP(id), HttpStatus.OK);
    }
    @GetMapping("api/chapter/{id}")
    @RolesAllowed(value = {"GET_CHAPTER"})
    public ResponseEntity<ChapitresDTO> findChapter(@PathVariable("id") Long id)throws Exception{
        return new ResponseEntity<>(services.getById(id), HttpStatus.OK);
    }

    @PutMapping("api/chapter/enable/{id}")
    @RolesAllowed(value = {"UPDATE_CHAPTER"})
    public ResponseEntity<?> enable(@PathVariable("id")Long id) throws Exception{
        services.enable(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("api/chapter/disable/{id}")
    @RolesAllowed(value = {"UPDATE_CHAPTER"})
    public ResponseEntity<?> disable(@PathVariable("id")Long id) throws Exception{
        services.disable(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "api/chapter/{id}")
    @RolesAllowed(value = {"CREATE_CHAPTER"})
    public ResponseEntity<?> create(@PathVariable("id")Long id, @RequestBody ChapitresDTO chapitresDTO) throws Exception{
        services.save(id, chapitresDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("api/chapter/{id}")
    @RolesAllowed(value = {"UPDATE_CHAPTER"})
    public ResponseEntity<?> update(@PathVariable("id")Long id, @RequestBody ChapitresDTO chapitresDTO) throws Exception{
        services.update(id, chapitresDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
