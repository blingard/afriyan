package org.ligot.afriyan.controller;

import jakarta.annotation.security.RolesAllowed;
import org.ligot.afriyan.Dto.ArticlesDTO;
import org.ligot.afriyan.Dto.ThemeTraiterDTO;
import org.ligot.afriyan.entities.Categorie;
import org.ligot.afriyan.entities.TypeDonne;
import org.ligot.afriyan.service.IArticles;
import org.ligot.afriyan.service.IThemeTraiter;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/theme")
public class ThemeTraiterController {
    private final IThemeTraiter service;

    public ThemeTraiterController(IThemeTraiter service) {
        this.service = service;
    }
    @GetMapping
    public List<ThemeTraiterDTO> findAllActive(){
        return service.getListActive();
    }
    @GetMapping("admin")
    @RolesAllowed(value = {"SUPERADMIN"})
    public List<ThemeTraiterDTO> findAll(){
        return service.getList();
    }
    @GetMapping("{id}")
    public ThemeTraiterDTO findById(@PathVariable("id")Long id) throws Exception {
        return service.findById(id);
    }
    @PutMapping("{id}")
    public void enableAndDesable(@PathVariable("id")Long id){
        service.active(id);
    }
    @PostMapping
    @RolesAllowed(value = {"SUPERADMIN"})
    public void create(@RequestBody ThemeTraiterDTO themeTraiterDTO){
        service.save(themeTraiterDTO);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

}
