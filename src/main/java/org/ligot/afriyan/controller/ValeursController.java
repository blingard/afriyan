package org.ligot.afriyan.controller;

import jakarta.annotation.security.RolesAllowed;
import org.ligot.afriyan.Dto.ValeursDTO;
import org.ligot.afriyan.service.IValeurs;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ValeursController {
    private final IValeurs service;

    public ValeursController(IValeurs service) {
        this.service = service;
    }

    @GetMapping("api/valeur")
    public List<ValeursDTO> findAll(){
        return service.getList();
    }
    @GetMapping("public/api/valeur/find-active")
    public List<ValeursDTO> findAllActive(){
        return service.getListActive();
    }

    @PostMapping("api/valeur")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public ValeursDTO create(@RequestBody ValeursDTO valeursDTO) throws Exception {
        return service.save(valeursDTO);
    }

    @GetMapping("api/valeur/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public Page<ValeursDTO> listAll(@PathVariable int id) throws Exception {
        return service.getPage(id);
    }

    @DeleteMapping("api/valeur/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public void delete(@PathVariable Long id) throws Exception {
        service.delete(id);
    }

    @GetMapping("api/valeur/find-by-id/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public ValeursDTO findById(@PathVariable Long id) throws Exception {
        return service.findById(id);
    }

    @PutMapping("api/valeur/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public void update(@PathVariable Long id, @RequestBody ValeursDTO valeursDTO) throws Exception {
        service.update(valeursDTO, id);
    }

    @PutMapping("api/valeur/active/{id}")
    /*@RolesAllowed(value = {"SUPERADMIN"})*/
    public void active(@PathVariable Long id) throws Exception {
        service.active(id);
    }
}
