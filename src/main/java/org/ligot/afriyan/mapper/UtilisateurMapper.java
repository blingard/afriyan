package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.entities.Utilisateur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper {
    Utilisateur create (UtilisateurDTO dto);
    UtilisateurDTO toDTO (Utilisateur entity);
    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "code", target = "code", ignore = true)
    @Mapping(source = "email", target = "email", ignore = true)
    @Mapping(source = "pwd", target = "pwd", ignore = true)
    void update(UtilisateurDTO dto, @MappingTarget Utilisateur utilisateur);
}
