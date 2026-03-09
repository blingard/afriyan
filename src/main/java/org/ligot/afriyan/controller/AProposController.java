package org.ligot.afriyan.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.ligot.afriyan.Dto.AProposDTO;
import org.ligot.afriyan.service.IAPropos;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AProposController {

    private final IAPropos service;

    public AProposController(IAPropos service) {
        this.service = service;
    }


    @PostMapping("api/apropos/save")
    @RolesAllowed(value = {"CREATE_ABOUT"})
    public ResponseEntity<?> saveCentre(
            @RequestBody @Valid AProposDTO aProposDTO) throws Exception {
        service.save(aProposDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "api/apropos/update/{id}")
    @RolesAllowed(value = {"UPDATE_ABOUT"})
    void updateCentre(@RequestBody @Valid AProposDTO aProposDTO, @PathVariable Long id) throws Exception {
        service.update(id, aProposDTO);
    }

    @GetMapping(value = "api/apropos/{id}")
    @RolesAllowed(value = {"GET_ABOUT"})
    AProposDTO updateCentre(@PathVariable Long id) throws Exception {
        return service.get(id);
    }

    @GetMapping(value = "public/api/apropos/list")
    AProposDTO listCentre() throws Exception {
        return service.get();
    }

}
