package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.ProduitDto;
import org.ligot.afriyan.entities.Produit;
import org.mapstruct.Mapper;

@Mapper
public interface ProduitMapper {

    Produit create (ProduitDto dto);
    ProduitDto toDto (Produit entity);
}
