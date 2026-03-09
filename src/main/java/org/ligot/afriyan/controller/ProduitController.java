package org.ligot.afriyan.controller;

import jakarta.annotation.security.RolesAllowed;
import org.ligot.afriyan.Dto.ProduitDTO;
import org.ligot.afriyan.service.IProduit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/produit")
public class ProduitController {

    @Autowired
    IProduit produit;

    @PostMapping(value = "/save")
    @RolesAllowed(value = {"CREATE_PRODUIT"})
    ProduitDTO saveProduit(@RequestBody ProduitDTO produitDto) throws Exception {
        return produit.save(produitDto);
    }

    @PutMapping(value = "/update/{id}")
    @RolesAllowed(value = {"UPDATE_PRODUIT"})
    ProduitDTO updateProduit(@RequestBody ProduitDTO produitDto, @PathVariable Long id) throws Exception {
        return produit.update(produitDto, id);
    }

    @GetMapping(value = "/list/{page}")
    @RolesAllowed(value = {"GET_PRODUIT"})
    Page<ProduitDTO> listProduit(@PathVariable  int page) throws Exception {
        return produit.list(page);
    }

    @DeleteMapping(value = "/delete/{id}")
    @RolesAllowed(value = {"DELETE_PRODUIT"})
    void deleteProduit (@PathVariable long id) throws Exception{
        produit.delete(id);
    }

    @GetMapping(value = "/getById/{id}")
    @RolesAllowed(value = {"GET_PRODUIT"})
    ProduitDTO listById(@PathVariable Long id) throws Exception {
        return produit.findById(id);
    }


}
