package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.CentrePartenaireDTO;
import org.ligot.afriyan.Dto.GroupesDTO;
import org.ligot.afriyan.entities.CentrePartenaire;
import org.ligot.afriyan.entities.Groupes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GroupesMapper {

    Groupes create (GroupesDTO dto);
    GroupesDTO toDTO (Groupes entity);

    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "roles", target = "roles", ignore = true)
    @Mapping(source = "utilisateurs", target = "utilisateurs", ignore = true)
    void update(GroupesDTO articlesDTO, @MappingTarget Groupes articles);

}
