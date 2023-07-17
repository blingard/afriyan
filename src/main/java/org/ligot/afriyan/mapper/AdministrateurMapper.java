package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.AdministrationDTO;
import org.ligot.afriyan.entities.Administrateur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AdministrateurMapper {
    Administrateur create (AdministrationDTO dto);
    AdministrationDTO toDTO (Administrateur entity);
    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "code", target = "code", ignore = true)
    @Mapping(source = "email", target = "email", ignore = true)
    void update(AdministrationDTO dto, @MappingTarget Administrateur administrateur);
}
