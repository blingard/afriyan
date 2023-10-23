package org.ligot.afriyan.controller;

import jakarta.annotation.security.RolesAllowed;
import org.ligot.afriyan.Dto.MissionsDTO;
import org.ligot.afriyan.entities.TypeDonne;
import org.ligot.afriyan.service.IMissions;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/missions")
public class MissionsController {
    private final IMissions service;

    public MissionsController(IMissions service) {
        this.service = service;
    }

    @GetMapping
    public List<MissionsDTO> findAllActive(){
        return service.getListActive();
    }
    @GetMapping("all")
    public List<MissionsDTO> findAll(){
        return service.getList();
    }

    @PostMapping
    @RolesAllowed(value = {"SUPERADMIN"})
    public MissionsDTO create(@RequestBody MissionsDTO missionsDTO){
        return service.saveM(missionsDTO);
    }

    @GetMapping("/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public Page<MissionsDTO> listAll(@PathVariable int id){
        return service.getPage(id);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @GetMapping("find-by-id/{id}")
    public MissionsDTO findById(@PathVariable Long id) throws Exception {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public void update(@PathVariable Long id, @RequestBody MissionsDTO missionsDTO) throws Exception {
        service.update(missionsDTO, id);
    }

    @PutMapping("active/{id}")
    /*@RolesAllowed(value = {"SUPERADMIN"})*/
    public void active(@PathVariable Long id) throws Exception {
        service.active(id);
    }

    @GetMapping("active/home")
    public List<MissionsDTO> find06Active(){
        return service.getList4Active();
    }
}
