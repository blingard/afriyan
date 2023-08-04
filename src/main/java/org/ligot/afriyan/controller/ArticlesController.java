package org.ligot.afriyan.controller;

import jakarta.annotation.security.RolesAllowed;
import org.ligot.afriyan.Dto.ArticlesDTO;
import org.ligot.afriyan.entities.TypeDonne;
import org.ligot.afriyan.service.IArticles;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/article")
public class ArticlesController {
    private final IArticles service;

    public ArticlesController(IArticles service) {
        this.service = service;
    }
    @GetMapping
    public List<ArticlesDTO> findAll(){
        return service.getList(TypeDonne.ARTICLE);
    }

    @GetMapping("active")
    public List<ArticlesDTO> findAllActive(){
        return service.getListActive(TypeDonne.ARTICLE);
    }

    @PostMapping
    /*@RolesAllowed(value = {"SUPERADMIN"})*/
    public ArticlesDTO create(@RequestBody ArticlesDTO valeursDTO){
        return service.save(valeursDTO);
    }

    @GetMapping("/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public Page<ArticlesDTO> listAll(@PathVariable int id){
        return service.getPage(id, TypeDonne.ARTICLE);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @GetMapping("find-by-id/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public ArticlesDTO findById(@PathVariable Long id) throws Exception {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public void update(@PathVariable Long id, @RequestBody ArticlesDTO valeursDTO) throws Exception {
        service.update(valeursDTO, id);
    }

    @PutMapping("active/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public void active(@PathVariable Long id) throws Exception {
        service.active( id, TypeDonne.ARTICLE);
    }
}
