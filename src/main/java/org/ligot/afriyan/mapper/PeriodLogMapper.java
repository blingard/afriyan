package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.PeriodLogDTO;
import org.ligot.afriyan.entities.PeriodLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PeriodLogMapper {

    @Mapping(target = "utilisateur", ignore = true)
    PeriodLog create(PeriodLogDTO dto);

    PeriodLogDTO toDTO(PeriodLog entity);

    List<PeriodLogDTO> toDTOList(List<PeriodLog> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "utilisateur", ignore = true)
    void update(PeriodLogDTO dto, @MappingTarget PeriodLog entity);
}
