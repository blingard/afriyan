package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.RapportDTO;
import org.ligot.afriyan.entities.Rapport;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RapportMapper {

    Rapport create (RapportDTO dto);
    RapportDTO toDTO(Rapport entity);
}
