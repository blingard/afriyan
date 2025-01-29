package org.ligot.afriyan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.ligot.afriyan.Dto.AProposDTO;
import org.ligot.afriyan.Dto.CentrePartenaireDTO;
import org.ligot.afriyan.service.IAPropos;
import org.ligot.afriyan.service.ICentrePartenaire;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value ={"api/apropos"})
public class AProposController {

    private final IAPropos service;

    public AProposController(IAPropos service) {
        this.service = service;
    }


    @PostMapping("save")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public ResponseEntity<?> saveCentre(
            @RequestBody @Valid AProposDTO aProposDTO) throws Exception {
        service.save(aProposDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    void updateCentre(@RequestBody @Valid AProposDTO aProposDTO, @PathVariable Long id) throws Exception {
        service.update(id, aProposDTO);
    }

    @GetMapping(value = "{id}")
    AProposDTO updateCentre(@PathVariable Long id) throws Exception {
        return service.get(id);
    }

    @GetMapping(value = "/list")
    AProposDTO listCentre() throws Exception {
        return service.get();
    }

}
