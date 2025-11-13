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
@Tag(name = "Paragraph", description = "Gestion des Paragraphes")
public class ParagraphController {
    private final ParagraphService services;

    public ParagraphController(ParagraphService services) {
        this.services = services;
    }

    @GetMapping("api/paragraph/{id}")
    public ResponseEntity<ParagraphsDTO> getParagraphe(@PathVariable("id") Long id)throws Exception{
        return new ResponseEntity<>(services.getById(id), HttpStatus.OK);
    }

    @GetMapping("api/paragraph/admin/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public ResponseEntity<ParagraphsDTO> getParagrapheAdmin(@PathVariable("id") Long id)throws Exception{
        return new ResponseEntity<>(services.getByIdAdmin(id), HttpStatus.OK);
    }

    @PutMapping("api/paragraph/enable/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    public ResponseEntity<?> enable(@PathVariable("id")Long id) throws Exception{
        services.enable(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("api/paragraph/disable/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    public ResponseEntity<?> disable(@PathVariable("id")Long id) throws Exception{
        services.disable(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("api/paragraph/update/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public ResponseEntity<?> update(@PathVariable("id")Long id, @RequestBody ParagraphsDTO paragraphsDTO) throws Exception{
        services.update(id, paragraphsDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "api/paragraph/{idChapter}")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public ResponseEntity<?> create(
            @PathVariable("idChapter") Long idChapter,
            @RequestParam(name = "file",required = false) MultipartFile file,
            @RequestParam( "jsonData") String jsonData) throws Exception {
        ParagraphsDTO paragraphsDTO = new ObjectMapper().readValue(jsonData, ParagraphsDTO.class);
        services.save(idChapter, paragraphsDTO, file);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
