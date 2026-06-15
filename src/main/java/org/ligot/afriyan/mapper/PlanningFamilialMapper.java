package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.PlanningFamilialDTO;
import org.ligot.afriyan.entities.PlanningFamilial;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PlanningFamilialMapper {

    @Mapping(target = "utilisateur", ignore = true)
    PlanningFamilial create(PlanningFamilialDTO dto);

    PlanningFamilialDTO toDTO(PlanningFamilial entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "utilisateur", ignore = true)
    void update(PlanningFamilialDTO dto, @MappingTarget PlanningFamilial entity);
}
