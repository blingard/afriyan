package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.ServiceDTO;
import org.ligot.afriyan.Dto.SouscriptionDTO;
import org.ligot.afriyan.entities.ServiceEntity;
import org.ligot.afriyan.entities.Souscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SouscriptionMapper {

    Souscription create (SouscriptionDTO souscriptionDTO);
    SouscriptionDTO toDTO (Souscription entity);

    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "status", target = "status", ignore = true)
    @Mapping(source = "service", target = "service", ignore = true)
    @Mapping(source = "utilisateur", target = "utilisateur", ignore = true)
    void update(SouscriptionDTO produitDTO, @MappingTarget Souscription produit);
}
