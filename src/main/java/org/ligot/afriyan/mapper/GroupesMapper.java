package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.GroupesDTO;
import org.ligot.afriyan.entities.Groupes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupesMapper {

    Groupes create (GroupesDTO dto);
    GroupesDTO toDTO (Groupes entity);
}
