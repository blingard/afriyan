package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.ProduitDTO;
import org.ligot.afriyan.entities.Produit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProduitMapper {

    Produit create (ProduitDTO dto);
    ProduitDTO toDTO (Produit entity);
}
