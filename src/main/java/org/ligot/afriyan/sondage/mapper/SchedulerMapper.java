package org.ligot.afriyan.sondage.mapper;

import org.ligot.afriyan.sondage.dto.SchedulerDTO;
import org.ligot.afriyan.sondage.entities.Scheduler;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SchedulerMapper {
    Scheduler toEntity(SchedulerDTO schedulerDTO);
    SchedulerDTO toDTO(Scheduler scheduler);

    @Mapping(source = "id", target = "id", ignore = true)
    void update(SchedulerDTO schedulerDTO, @MappingTarget Scheduler scheduler);
}
