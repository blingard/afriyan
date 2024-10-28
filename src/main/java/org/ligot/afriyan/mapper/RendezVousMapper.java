package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.RendezVousDTO;
import org.ligot.afriyan.entities.RendezVous;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface RendezVousMapper {

    RendezVous create (RendezVousDTO dto);
    @Mapping(source = "utilisateur", target = "utilisateur", ignore = true)
    RendezVousDTO toDTO (RendezVous entity);

}
