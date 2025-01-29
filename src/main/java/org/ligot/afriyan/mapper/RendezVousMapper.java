package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.RendezVousDTO;
import org.ligot.afriyan.entities.RendezVous;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")

public interface RendezVousMapper {

    RendezVous create (RendezVousDTO dto);
    @Mapping(source = "utilisateur.pwd", target = "utilisateur.pwd", ignore = true)
    RendezVousDTO toDTO (RendezVous entity);

    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "libelle", target = "libelle", ignore = true)
    @Mapping(source = "dateRdv", target = "dateRdv", ignore = true)
    @Mapping(source = "heureDebut", target = "heureDebut", ignore = true)
    @Mapping(source = "heureFin", target = "heureFin", ignore = true)
    @Mapping(source = "utilisateur", target = "utilisateur", ignore = true)
    @Mapping(source = "centrePartenaire", target = "centrePartenaire", ignore = true)
    @Mapping(source = "serviceEntity", target = "serviceEntity", ignore = true)
    void update(RendezVousDTO dto, @MappingTarget RendezVous rendezVous);

}
