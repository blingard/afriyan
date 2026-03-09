package org.ligot.afriyan.controller;

import jakarta.annotation.security.RolesAllowed;
import org.ligot.afriyan.Dto.MissionsDTO;
import org.ligot.afriyan.entities.TypeDonne;
import org.ligot.afriyan.service.IMissions;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MissionsController {
    private final IMissions service;

    public MissionsController(IMissions service) {
        this.service = service;
    }

    @GetMapping("public/api/missions")
    public List<MissionsDTO> findAllActive(){
        return service.getListActive();
    }
    @GetMapping("api/missions/all")
    @RolesAllowed(value = {"GET_MISSION"})
    public List<MissionsDTO> findAll(){
        return service.getList();
    }

    @PostMapping("api/missions")
    @RolesAllowed(value = {"CREATE_MISSION"})
    public MissionsDTO create(@RequestBody MissionsDTO missionsDTO){
        return service.saveM(missionsDTO);
    }

    @GetMapping("/api/missions/{id}")
    @RolesAllowed(value = {"GET_MISSION"})
    public Page<MissionsDTO> listAll(@PathVariable int id){
        return service.getPage(id);
    }

    @DeleteMapping("/api/missions/{id}")
    @RolesAllowed(value = {"DELETE_MISSION"})
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @GetMapping("api/missions/find-by-id/{id}")
    public MissionsDTO findById(@PathVariable Long id) throws Exception {
        return service.findById(id);
    }

    @PutMapping("api/missions/{id}")
    @RolesAllowed(value = {"UPDATE_MISSION"})
    public void update(@PathVariable Long id, @RequestBody MissionsDTO missionsDTO) throws Exception {
        service.update(missionsDTO, id);
    }

    @PutMapping("api/missions/active/{id}")
    @RolesAllowed(value = {"UPDATE_MISSION"})
    /*@RolesAllowed(value = {"SUPERADMIN"})*/
    public void active(@PathVariable Long id) throws Exception {
        service.active(id);
    }

    @GetMapping("api/missions/active/home")
    public List<MissionsDTO> find06Active(){
        return service.getList4Active();
    }
}
