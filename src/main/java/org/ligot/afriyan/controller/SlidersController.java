package org.ligot.afriyan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.ligot.afriyan.Dto.CentrePartenaireDTO;
import org.ligot.afriyan.Dto.PageDTO;
import org.ligot.afriyan.Dto.SlidersDTO;
import org.ligot.afriyan.service.ISliders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value ={"api/sliders"})
public class SlidersController {

    private final ISliders service;

    public SlidersController(ISliders service) {
        this.service = service;
    }



    @GetMapping
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public PageDTO<SlidersDTO> getAll(@RequestParam(name = "page", defaultValue = "0")int page) throws Exception {
        return service.getListAll(page);
    }

    @GetMapping("all")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public List<SlidersDTO> getAll() throws Exception {
        return service.getListAll();
    }

    @PostMapping("save")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public ResponseEntity<?> saveCertificates(
            @RequestParam(name = "file",required = false) MultipartFile file,
            @RequestParam( "jsonData") String jsonData) throws Exception {
        SlidersDTO slidersDTO = new ObjectMapper().readValue(jsonData, SlidersDTO.class);
        service.save(file, slidersDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    void updateCentre(@RequestBody @Valid SlidersDTO certificatesDTO, @PathVariable Long id) throws Exception {
        service.update(certificatesDTO, id);
    }

    @GetMapping(value = "{id}")
    SlidersDTO updateCentre(@PathVariable Long id) throws Exception {
        return service.findById(id);
    }

    @GetMapping(value = "/to_use")
    List<SlidersDTO> toUse() throws Exception {
        return service.findToUse();
    }

    @PutMapping(value = "/change_status/{id}")
    void changeStatus(@PathVariable Long id) throws Exception {
        service.active(id);
    }

}
