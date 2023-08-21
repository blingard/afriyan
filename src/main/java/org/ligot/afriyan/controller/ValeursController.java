package org.ligot.afriyan.controller;

import jakarta.annotation.security.RolesAllowed;
import org.ligot.afriyan.Dto.ValeursDTO;
import org.ligot.afriyan.service.IValeurs;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/valeur")
public class ValeursController {
    private final IValeurs service;

    public ValeursController(IValeurs service) {
        this.service = service;
    }

    @GetMapping
    public List<ValeursDTO> findAll(){
        return service.getList();
    }
    @GetMapping("find-active")
    public List<ValeursDTO> findAllActive(){
        return service.getListActive();
    }

    @PostMapping
    @RolesAllowed(value = {"SUPERADMIN"})
    public ValeursDTO create(@RequestBody ValeursDTO valeursDTO) throws Exception {
        return service.save(valeursDTO);
    }

    @GetMapping("/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public Page<ValeursDTO> listAll(@PathVariable int id) throws Exception {
        return service.getPage(id);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public void delete(@PathVariable Long id) throws Exception {
        service.delete(id);
    }

    @GetMapping("find-by-id/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public ValeursDTO findById(@PathVariable Long id) throws Exception {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public void update(@PathVariable Long id, @RequestBody ValeursDTO valeursDTO) throws Exception {
        service.update(valeursDTO, id);
    }

    @PutMapping("active/{id}")
    /*@RolesAllowed(value = {"SUPERADMIN"})*/
    public void active(@PathVariable Long id) throws Exception {
        service.active(id);
    }
}
