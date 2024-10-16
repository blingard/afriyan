package org.ligot.afriyan.elearning.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import org.ligot.afriyan.elearning.dto.CommentsDTO;
import org.ligot.afriyan.elearning.dto.ParagraphsDTO;
import org.ligot.afriyan.elearning.service.CommentsServices;
import org.ligot.afriyan.elearning.service.ParagraphService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/test/api/paragraph")
@Tag(name = "Paragraph", description = "Gestion des Paragraphes")
public class ParagraphController {
    private final ParagraphService services;

    public ParagraphController(ParagraphService services) {
        this.services = services;
    }

    @GetMapping("{id}")
    public ResponseEntity<ParagraphsDTO> getParagraphe(@PathVariable("id") Long id)throws Exception{
        return new ResponseEntity<>(services.getById(id), HttpStatus.OK);
    }

    @GetMapping("admin/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public ResponseEntity<ParagraphsDTO> getParagrapheAdmin(@PathVariable("id") Long id)throws Exception{
        return new ResponseEntity<>(services.getByIdAdmin(id), HttpStatus.OK);
    }

    @PutMapping("enable/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public ResponseEntity<?> enable(@PathVariable("id")Long id) throws Exception{
        services.enable(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("disable/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public ResponseEntity<?> disable(@PathVariable("id")Long id) throws Exception{
        services.disable(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "{idChapter}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public ResponseEntity<?> create(
            @PathVariable("idChapter") Long idChapter,
            @RequestParam(name = "file",required = false) MultipartFile file,
            @RequestParam( "jsonData") String jsonData) throws Exception {
        ParagraphsDTO paragraphsDTO = new ObjectMapper().readValue(jsonData, ParagraphsDTO.class);
        services.save(idChapter, paragraphsDTO, file);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
