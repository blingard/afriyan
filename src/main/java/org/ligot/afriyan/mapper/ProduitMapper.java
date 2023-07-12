package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.ProduitDTO;
import org.ligot.afriyan.entities.Produit;
import org.mapstruct.Mapper;

@Mapper
public interface ProduitMapper {

    Produit create (ProduitDTO dto);
    ProduitDTO toDto (Produit entity);
}
