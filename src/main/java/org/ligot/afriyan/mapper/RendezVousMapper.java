package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.RendezVousDTO;
import org.ligot.afriyan.entities.RendezVous;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface RendezVousMapper {

    RendezVous create (RendezVousDTO dto);
    RendezVousDTO toDTO (RendezVous entity);

}
