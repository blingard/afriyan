package org.ligot.afriyan.controller;

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
    ProduitDTO saveProduit(@RequestBody ProduitDTO produitDto) throws Exception {
        return produit.save(produitDto);
    }

    @PutMapping(value = "/update/{id}")
    ProduitDTO updateProduit(@RequestBody ProduitDTO produitDto, @PathVariable Long id) throws Exception {
        return produit.update(produitDto, id);
    }

    @GetMapping(value = "/list/{page}")
    Page<ProduitDTO> listProduit(@PathVariable  int page) throws Exception {
        return produit.list(page);
    }

    @DeleteMapping(value = "/delete")
    void deleteProduit (@PathVariable long id) throws Exception{
        produit.delete(id);
    }

    @GetMapping(value = "/getById/{id}")
    ProduitDTO listById(@PathVariable Long id) throws Exception {
        return produit.findById(id);
    }


}
