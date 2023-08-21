package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.ParametresDto;
import org.ligot.afriyan.entities.Parametres;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ParametresMapper {
    ParametresDto toDTO(Parametres parametres);
    Parametres create(ParametresDto parametresDto);
    @Mapping(source = "id", target = "id",ignore = true)
    @Mapping(source = "status", target = "status",ignore = true)
    @Mapping(source = "paramTypeEnum", target = "paramTypeEnum",ignore = true)
    void update(ParametresDto parametresDto, @MappingTarget Parametres parametres);
}
