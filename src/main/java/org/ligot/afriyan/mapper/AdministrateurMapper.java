package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.AdministrationDTO;
import org.ligot.afriyan.entities.Administrateur;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdministrateurMapper {
    Administrateur create (AdministrationDTO dto);
    AdministrationDTO toDTO (Administrateur entity);
}
