package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.CentrePartenaireDTO;
import org.ligot.afriyan.Dto.ProduitDTO;
import org.ligot.afriyan.entities.CentrePartenaire;
import org.ligot.afriyan.entities.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CentrePartenaireMapper {

    CentrePartenaire create (CentrePartenaireDTO dto);
    CentrePartenaireDTO toDTO (CentrePartenaire entity);
}
