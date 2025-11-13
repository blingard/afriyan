package org.ligot.afriyan.sondage.mapper;

import org.ligot.afriyan.Dto.CategoriesDTO;
import org.ligot.afriyan.entities.Categories;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoriesMapper {

    Categories toEntity(CategoriesDTO categoriesDTO);
    CategoriesDTO toDTO(Categories answer);
    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "code", target = "code", ignore = true)
    @Mapping(source = "status", target = "status", ignore = true)
    void update(CategoriesDTO categoriesDTO, @MappingTarget Categories categories);
}
