package org.ligot.afriyan.sondage.mapper;

import org.ligot.afriyan.sondage.dto.CategorieEntitiesDTO;
import org.ligot.afriyan.sondage.entities.CategorieEntities;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategorieEntitieMapper {

    CategorieEntities toEntity(CategorieEntitiesDTO categorieEntitiesDTO);
    CategorieEntitiesDTO toDTO(CategorieEntities answer);
}
