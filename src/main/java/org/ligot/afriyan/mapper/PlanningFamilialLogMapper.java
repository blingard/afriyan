package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.PlanningFamilialLogDTO;
import org.ligot.afriyan.entities.PlanningFamilialLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PlanningFamilialLogMapper {

    @Mapping(target = "planningFamilial", ignore = true)
    PlanningFamilialLog create(PlanningFamilialLogDTO dto);

    PlanningFamilialLogDTO toDTO(PlanningFamilialLog entity);

    List<PlanningFamilialLogDTO> toDTOList(List<PlanningFamilialLog> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "planningFamilial", ignore = true)
    void update(PlanningFamilialLogDTO dto, @MappingTarget PlanningFamilialLog entity);
}
