package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.ProduitDTO;
import org.ligot.afriyan.entities.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProduitMapper {

    Produit create (ProduitDTO dto);
    ProduitDTO toDTO (Produit entity);
}
