package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.ArticlesDTO;
import org.ligot.afriyan.entities.Articles;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ArticlesMapper {

    ArticlesDTO toDTO(Articles articles);
    Articles create(ArticlesDTO articlesDTO);
    @Mapping(source = "id", target = "id",ignore = true)
    @Mapping(source = "type", target = "type",ignore = true)
    void update(ArticlesDTO articlesDTO, @MappingTarget Articles articles);
}
