package org.ligot.afriyan.controller;

import jakarta.annotation.security.RolesAllowed;
import org.ligot.afriyan.Dto.OurWorksDTO;
import org.ligot.afriyan.entities.OurWorksType;
import org.ligot.afriyan.service.IOurWorks;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/our-work")
public class OurWorksController {
    private final IOurWorks service;

    public OurWorksController(IOurWorks service) {
        this.service = service;
    }
    @GetMapping("/{type}")
    public List<OurWorksDTO> findAllActive(@PathVariable String type){
        return service.getList(OurWorksType.valueOf(type));
    }

    @PostMapping
    @RolesAllowed(value = {"SUPERADMIN"})
    public OurWorksDTO create(@RequestBody OurWorksDTO valeursDTO){
        return service.save(valeursDTO);
    }

    @GetMapping("/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public Page<OurWorksDTO> listAll(@PathVariable int id){
        return service.getPage(id);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @GetMapping("find-by-id/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public OurWorksDTO findById(@PathVariable Long id) throws Exception {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public void update(@PathVariable Long id, @RequestBody OurWorksDTO valeursDTO) throws Exception {
        service.update(valeursDTO, id);
    }
}
