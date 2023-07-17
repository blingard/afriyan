package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.SouscriptionDTO;
import org.ligot.afriyan.entities.Souscription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SouscriptionMapper {

    Souscription create (SouscriptionDTO souscriptionDTO);
    SouscriptionDTO toDTO (Souscription entity);
}
