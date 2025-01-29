package org.ligot.afriyan.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.ligot.afriyan.Dto.CertificatesDTO;
import org.ligot.afriyan.Dto.PageDTO;
import org.ligot.afriyan.service.ICertificates;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value ={"api/certificate"})
public class CertificatesController {

    private final ICertificates service;

    public CertificatesController(ICertificates service) {
        this.service = service;
    }



    @GetMapping
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public PageDTO<CertificatesDTO> getAll(@RequestParam(name = "page", defaultValue = "0")int page) throws Exception {
        return service.getListAll(page);
    }

    @PostMapping("save")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public ResponseEntity<?> saveCertificates(
            @RequestBody @Valid CertificatesDTO certificatesDTO) throws Exception {
        service.save(certificatesDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    void updateCentre(@RequestBody @Valid CertificatesDTO certificatesDTO, @PathVariable Long id) throws Exception {
        service.update(certificatesDTO, id);
    }

    @GetMapping(value = "{id}")
    CertificatesDTO updateCentre(@PathVariable Long id) throws Exception {
        return service.findById(id);
    }

    @GetMapping(value = "/to_use")
    CertificatesDTO toUse() throws Exception {
        return service.findToUse();
    }

    @PutMapping(value = "/change_status/{id}")
    void changeStatus(@PathVariable Long id) throws Exception {
        service.active(id);
    }

}
