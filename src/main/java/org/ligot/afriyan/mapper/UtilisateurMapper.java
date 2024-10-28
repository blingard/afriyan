package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.entities.Utilisateur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper {
    Utilisateur create (UtilisateurDTO dto);

    @Mapping(source = "pwd", target = "pwd", ignore = true)
    UtilisateurDTO toDTO (Utilisateur entity);
    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "code", target = "code", ignore = true)
    @Mapping(source = "email", target = "email", ignore = true)
    @Mapping(source = "pwd", target = "pwd", ignore = true)
    @Mapping(source = "ddn", target = "ddn", ignore = true)
    @Mapping(source = "lieu", target = "lieu", ignore = true)
    @Mapping(source = "numero_telephone", target = "numero_telephone", ignore = true)
    @Mapping(source = "photo", target = "photo", ignore = true)
    @Mapping(source = "location", target = "location", ignore = true)
    @Mapping(source = "anonymat", target = "anonymat", ignore = true)
    @Mapping(source = "sexe", target = "sexe", ignore = true)
    @Mapping(source = "status", target = "status", ignore = true)
    @Mapping(source = "isFirstConnexion", target = "isFirstConnexion", ignore = true)
    @Mapping(source = "groupe", target = "groupe", ignore = true)
    void update(UtilisateurDTO dto, @MappingTarget Utilisateur utilisateur);
}
