package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.ArticlesDTO;
import org.ligot.afriyan.Dto.CentrePartenaireDTO;
import org.ligot.afriyan.Dto.ProduitDTO;
import org.ligot.afriyan.entities.Articles;
import org.ligot.afriyan.entities.CentrePartenaire;
import org.ligot.afriyan.entities.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CentrePartenaireMapper {

    CentrePartenaire create (CentrePartenaireDTO dto);
    CentrePartenaireDTO toDTO (CentrePartenaire entity);

    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "status", target = "status", ignore = true)
    @Mapping(source = "serviceOfferts", target = "serviceOfferts", ignore = true)
    @Mapping(source = "createur", target = "createur", ignore = true)
    @Mapping(source = "photo", target = "photo", ignore = true)
    void update(CentrePartenaireDTO articlesDTO, @MappingTarget CentrePartenaire articles);

}
