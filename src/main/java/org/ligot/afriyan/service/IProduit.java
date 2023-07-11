package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.ProduitDto;
import org.ligot.afriyan.entities.Produit;

import java.util.List;

public interface IProduit {

    Produit saveProduit(ProduitDto produitDto);
    List<Produit> listProduit();
    Produit updateProduit(ProduitDto produitDto, long id);
    void deleteProduit(long id);

}
