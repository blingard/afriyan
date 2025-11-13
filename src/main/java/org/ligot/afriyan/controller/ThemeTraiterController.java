package org.ligot.afriyan.controller;

import jakarta.annotation.security.RolesAllowed;
import org.ligot.afriyan.Dto.ThemeTraiterDTO;
import org.ligot.afriyan.service.IThemeTraiter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ThemeTraiterController {
    private final IThemeTraiter service;

    public ThemeTraiterController(IThemeTraiter service) {
        this.service = service;
    }
    @GetMapping("public/api/theme")
    public List<ThemeTraiterDTO> findAllActive(){
        return service.getListActive();
    }
    @GetMapping("api/theme/admin")
    public List<ThemeTraiterDTO> findAll(){
        return service.getList();
    }
    @GetMapping("api/theme/{id}")
    public ThemeTraiterDTO findById(@PathVariable("id")Long id) throws Exception {
        return service.findById(id);
    }
    @PutMapping("api/theme/{id}")
    public void enableAndDesable(@PathVariable("id")Long id){
        service.active(id);
    }
    @PostMapping("api/theme")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public void create(@RequestBody ThemeTraiterDTO themeTraiterDTO){
        service.save(themeTraiterDTO);
    }

    @DeleteMapping("/api/theme/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

}
