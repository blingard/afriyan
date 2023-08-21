package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.GroupesDTO;
import org.ligot.afriyan.Dto.ProduitDTO;
import org.ligot.afriyan.entities.Groupes;
import org.ligot.afriyan.entities.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProduitMapper {

    Produit create (ProduitDTO dto);
    ProduitDTO toDTO (Produit entity);
    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "service", target = "service", ignore = true)
    @Mapping(source = "dateCreation", target = "datCreation", ignore = true)
    void update(ProduitDTO produitDTO, @MappingTarget Produit produit);
}
