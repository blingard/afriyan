package org.ligot.afriyan.controller;

import jakarta.annotation.security.RolesAllowed;
import org.ligot.afriyan.Dto.ArticlesDTO;
import org.ligot.afriyan.entities.TypeDonne;
import org.ligot.afriyan.service.IArticles;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TemoignageController {
    private final IArticles service;

    public TemoignageController(IArticles service) {
        this.service = service;
    }

    @GetMapping("api/temoignage")
    public List<ArticlesDTO> findAll(){
        return service.getList(TypeDonne.TEMOIGNAGE);
    }
    @GetMapping("public/api/temoignage/active")
    public List<ArticlesDTO> findAllActive(){
        return service.getListActive(TypeDonne.TEMOIGNAGE);
    }

    @PostMapping("api/temoignage")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public ArticlesDTO create(@RequestBody ArticlesDTO valeursDTO) throws Exception {
        return service.save(valeursDTO);
    }

    @GetMapping("api/temoignage/{id}")
    public Page<ArticlesDTO> listAll(@PathVariable int id){
        return service.getPage(id, TypeDonne.TEMOIGNAGE);
    }

    @DeleteMapping("api/temoignage/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @GetMapping("api/temoignage/find-by-id/{id}")
    public ArticlesDTO findById(@PathVariable Long id) throws Exception {
        return service.findById(id);
    }

    @GetMapping("api/temoignage/getById/{id}")
    public ArticlesDTO findByIdActive(@PathVariable Long id) throws Exception {
        return service.findByIdActive(id);
    }

    @GetMapping("api/temoignage/active/home")
    public List<ArticlesDTO> find06Active(){
        return service.get6TopDesc(TypeDonne.TEMOIGNAGE);
    }


    @PutMapping("api/temoignage/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public void update(@PathVariable Long id, @RequestBody ArticlesDTO valeursDTO) throws Exception {
        service.update(valeursDTO, id);
    }
    @GetMapping("api/temoignage/get-by-categorie/{categorie}")
    public List<ArticlesDTO> findAllArticleByCategorieAdmin(@PathVariable String categorie){
        return service.getListAdmin(TypeDonne.TEMOIGNAGE, categorie);
    }
    @GetMapping("public/api/temoignage/get-by-categorie/{menusId}")
    public List<ArticlesDTO> findAllArticleByCategorie(@PathVariable String menusId){
        return service.getList(TypeDonne.ARTICLE, menusId);
    }

    @PutMapping("api/temoignage/active/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    public void active(@PathVariable Long id) throws Exception {
        service.active( id, TypeDonne.TEMOIGNAGE);
    }
}
