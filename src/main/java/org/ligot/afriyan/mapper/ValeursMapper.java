package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.ValeursDTO;
import org.ligot.afriyan.entities.Valeurs;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ValeursMapper {
    ValeursDTO toDTO(Valeurs valeurs);
    Valeurs create(ValeursDTO valeursDTO);
    @Mapping(source = "id", target = "id",ignore = true)
    void update(ValeursDTO valeursDTO, @MappingTarget Valeurs valeurs);
}
