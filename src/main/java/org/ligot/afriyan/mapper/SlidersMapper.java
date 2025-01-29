package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.SlidersDTO;
import org.ligot.afriyan.entities.Sliders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SlidersMapper {

    SlidersDTO toDTO(Sliders certificates);
    Sliders create(SlidersDTO articlesDTO);

    @Mapping(source = "id", target = "id", ignore = true)
    void update(SlidersDTO certificatesDTO, @MappingTarget Sliders certificates);
}
