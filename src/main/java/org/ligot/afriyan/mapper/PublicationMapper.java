package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.PublicationsDTO;
import org.ligot.afriyan.Dto.RolesDTO;
import org.ligot.afriyan.entities.Publications;
import org.ligot.afriyan.entities.Roles;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface PublicationMapper {
    Publications create (PublicationsDTO dto);
    PublicationsDTO toDTO (Publications entity);
}
