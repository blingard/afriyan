package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.ThemeTraiterDTO;
import org.ligot.afriyan.entities.ThemeTraiter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ThemeTraiterMapper {

    ThemeTraiterDTO toDTO(ThemeTraiter themeTraiter);
    ThemeTraiter create(ThemeTraiterDTO themeTraiterDTO);
    @Mapping(source = "id", target = "id", ignore = true)
    void update(ThemeTraiterDTO themeTraiterDTO, @MappingTarget ThemeTraiter themeTraiter);
}
