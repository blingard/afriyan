package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.CycleMenstruelSettingsDTO;
import org.ligot.afriyan.entities.CycleMenstruelSettings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CycleMenstruelMapper {

    @Mapping(target = "utilisateur", ignore = true)
    CycleMenstruelSettings create(CycleMenstruelSettingsDTO dto);

    CycleMenstruelSettingsDTO toDTO(CycleMenstruelSettings entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "utilisateur", ignore = true)
    void update(CycleMenstruelSettingsDTO dto, @MappingTarget CycleMenstruelSettings entity);
}
