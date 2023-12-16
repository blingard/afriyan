package org.ligot.afriyan.sondage.mapper;

import org.ligot.afriyan.sondage.dto.SondageDTO;
import org.ligot.afriyan.sondage.entities.Sondage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SondageMapper {
    Sondage toEntity(SondageDTO sondageDTO);
    SondageDTO toDTO(Sondage sondage);

    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "state", target = "state", ignore = true)
    @Mapping(source = "questions", target = "questions", ignore = true)
    @Mapping(source = "scheduler", target = "scheduler", ignore = true)
    @Mapping(source = "createDate", target = "createDate", ignore = true)
    @Mapping(source = "createUser", target = "createUser", ignore = true)
    void update(SondageDTO sondageDTO, @MappingTarget Sondage sondage);
}
