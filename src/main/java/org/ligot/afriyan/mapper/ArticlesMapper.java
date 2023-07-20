package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.ArticlesDTO;
import org.ligot.afriyan.entities.Articles;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticlesMapper {

    ArticlesDTO toDTO(Articles articles);
    Articles create(ArticlesDTO articlesDTO);
}
