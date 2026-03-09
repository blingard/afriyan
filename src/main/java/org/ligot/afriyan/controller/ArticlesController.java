package org.ligot.afriyan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.security.RolesAllowed;
import org.ligot.afriyan.Dto.ArticlesDTO;
import org.ligot.afriyan.entities.TypeDonne;
import org.ligot.afriyan.service.IArticles;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ArticlesController {
    private final IArticles service;

    public ArticlesController(IArticles service) {
        this.service = service;
    }
    @GetMapping("api/article")
    @RolesAllowed(value = {"GET_ARTICLE"})
    public List<ArticlesDTO> findAll(){
        return service.getList(TypeDonne.ARTICLE);
    }

    @GetMapping("public/api/article/get-by-categorie/{menusId}")
    public List<ArticlesDTO> findAllArticleByCategorie(@PathVariable String menusId){
        return service.getList(TypeDonne.ARTICLE, menusId);
    }

    @GetMapping("api/article/get-by-categorie/{categoriesId}")
    @RolesAllowed(value = {"GET_ARTICLE"})
    public List<ArticlesDTO> findAllArticleByCategorieAdmin(@PathVariable String categoriesId){
        return service.getListAdmin(TypeDonne.ARTICLE, categoriesId);
    }

    @GetMapping("api/article/active")
    @RolesAllowed(value = {"GET_ARTICLE"})
    public List<ArticlesDTO> findAllActive(){
        return service.getListActive(TypeDonne.ARTICLE);
    }
    @GetMapping("public/api/article/active/home")
    public List<ArticlesDTO> find06Active(){
        return service.get6TopDesc(TypeDonne.ARTICLE);
    }

    @PostMapping("api/article")
    @RolesAllowed(value = {"GET_ARTICLE"})
    public ArticlesDTO create(@RequestParam(name = "file",required = false) MultipartFile file,
                              @RequestParam( "jsonData") String jsonData)throws Exception{
        ArticlesDTO articlesDTO = new ObjectMapper().readValue(jsonData, ArticlesDTO.class);
        return service.save(file, articlesDTO);
    }

    @GetMapping("/api/article/{id}")
    @RolesAllowed(value = {"GET_ARTICLE"})
    public Page<ArticlesDTO> listAll(@PathVariable int id){
        return service.getPage(id, TypeDonne.ARTICLE);
    }

    @DeleteMapping("/api/article/{id}")
    @RolesAllowed(value = {"DELETE_ARTICLE"})
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @GetMapping("api/article/find-by-id/{id}")
    @RolesAllowed(value = {"GET_ARTICLE"})
    public ArticlesDTO findById(@PathVariable Long id) throws Exception {
        return service.findById(id);
    }
    @GetMapping("public/api/article/get/{id}")
    public ArticlesDTO findId(@PathVariable Long id) throws Exception {
        return service.findByIdActive(id);
    }

    @PutMapping("api/article/{id}")
    @RolesAllowed(value = {"UPDATE_ARTICLE"})
    public void update(@PathVariable Long id, @RequestBody ArticlesDTO valeursDTO) throws Exception {
        service.update(valeursDTO, id);
    }

    @PutMapping("api/article/active/{id}")
    @RolesAllowed(value = {"UPDATE_ARTICLE"})
    public void active(@PathVariable Long id) throws Exception {
        service.active( id, TypeDonne.ARTICLE);
    }
}
