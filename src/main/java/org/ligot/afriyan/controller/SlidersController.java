package org.ligot.afriyan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.ligot.afriyan.Dto.CentrePartenaireDTO;
import org.ligot.afriyan.Dto.PageDTO;
import org.ligot.afriyan.Dto.SlidersDTO;
import org.ligot.afriyan.Dto.SlidersSmartDTO;
import org.ligot.afriyan.entities.FrontType;
import org.ligot.afriyan.service.ISliders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class SlidersController {

    private final ISliders service;

    public SlidersController(ISliders service) {
        this.service = service;
    }



    @GetMapping("api/sliders")
    @RolesAllowed(value = {"GET_SLIDER"})
    public PageDTO<SlidersDTO> getAll(@RequestParam(name = "page", defaultValue = "0")int page) throws Exception {
        return service.getListAll(page);
    }

    @GetMapping("api/sliders/all")
    @RolesAllowed(value = {"GET_SLIDER"})
    public List<SlidersDTO> getAll() throws Exception {
        return service.getListAll();
    }

    @PostMapping("api/sliders/save")
    @RolesAllowed(value = {"CREATE_SLIDER"})
    public ResponseEntity<?> saveSlide(
            @RequestParam(name = "file",required = false) MultipartFile file,
            @RequestParam( "jsonData") String jsonData) throws Exception {
        SlidersDTO slidersDTO = new ObjectMapper().readValue(jsonData, SlidersDTO.class);
        service.save(file, slidersDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "api/sliders/update/{id}")
    @RolesAllowed(value = {"UPDATE_SLIDER"})
    public void updateSlide(@RequestBody @Valid SlidersDTO slidersDTO, @PathVariable Long id) throws Exception {
        service.update(slidersDTO, id);
    }

    @PutMapping(value = "api/sliders/update/file/{id}")
    @RolesAllowed(value = {"UPDATE_SLIDER"})
    public void updateFileSlide(
            @RequestParam(name = "file",required = false) MultipartFile file,
            @PathVariable Long id) throws Exception {
        service.updateFileSlide(file, id);
    }

    @GetMapping(value = "api/sliders/{id}")
    public SlidersDTO updateCentre(@PathVariable Long id) throws Exception {
        return service.findById(id);
    }

    @GetMapping(value = "public/sliders/{id}")
    public SlidersDTO findPublic(@PathVariable Long id) throws Exception {
        return service.findById(id);
    }

    @GetMapping(value = "public/api/sliders/to_use")
    public List<SlidersDTO> toUse() throws Exception {
        return service.findToUse();
    }

    @GetMapping(value = "public/api/sliders/by/front/{frontType}")
    public List<SlidersSmartDTO> toUse(@PathVariable("frontType")FrontType frontType) throws Exception {
        return service.findToUse(frontType);
    }

    @PutMapping(value = "/api/sliders/change_status/{id}")
    @RolesAllowed(value = {"UPDATE_SLIDER"})
    public void changeStatus(@PathVariable Long id) throws Exception {
        service.active(id);
    }

}
