package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.MissionsDTO;
import org.ligot.afriyan.entities.Missions;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MissionsMapper {
    MissionsDTO toDTO(Missions missions);
    Missions create(MissionsDTO missions);
    @Mapping(source = "id", target = "id", ignore = true)
    void update(MissionsDTO missionsDTO, @MappingTarget Missions missions);
}
