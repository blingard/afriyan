package org.ligot.afriyan.controller;

import jakarta.annotation.security.RolesAllowed;
import org.ligot.afriyan.Dto.ProduitDTO;
import org.ligot.afriyan.Dto.ProduitRequest;
import org.ligot.afriyan.service.IProduit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProduitController {

    @Autowired
    IProduit produit;

    @PostMapping(value = "api/produit/save")
    @RolesAllowed(value = {"CREATE_PRODUIT"})
    public ProduitDTO saveProduit(@RequestBody ProduitRequest produitRequest) throws Exception {
        return produit.save(produitRequest);
    }

    @PutMapping(value = "api/produit/update/{id}")
    @RolesAllowed(value = {"UPDATE_PRODUIT"})
    public ProduitDTO updateProduit(@RequestBody ProduitDTO produitDto, @PathVariable Long id) throws Exception {
        return produit.update(produitDto, id);
    }

    @GetMapping(value = "api/produit/list/{page}")
    @RolesAllowed(value = {"GET_PRODUIT"})
    public Page<ProduitDTO> listProduit(@PathVariable  int page) throws Exception {
        return produit.list(page);
    }

    @DeleteMapping(value = "api/produit/delete/{id}")
    @RolesAllowed(value = {"DELETE_PRODUIT"})
    public void deleteProduit (@PathVariable long id) throws Exception{
        //produit.delete(id);
    }

    @PutMapping(value = "api/produit/change-status/{id}")
    @RolesAllowed(value = {"UPDATE_PRODUIT"})
    public void changeStatus (@PathVariable long id) throws Exception{
        produit.delete(id);
    }

    @GetMapping(value = "api/produit/getById/{id}")
    @RolesAllowed(value = {"GET_PRODUIT"})
    public ProduitDTO listById(@PathVariable Long id) throws Exception {
        return produit.findById(id);
    }


}
