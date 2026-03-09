package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.init.PermissionEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper {
    Utilisateur create (UtilisateurDTO dto);

    @Mapping(source = "pwd", target = "pwd", qualifiedByName = "password")
    @Mapping(source = ".", target = "permissions", qualifiedByName = "mapPermissions")
    UtilisateurDTO toDTO(Utilisateur entity);

    @Named("mapPermissions")
    default Set<PermissionEnum> mapPermissions(Utilisateur entity) {
        return entity.getEffectivePermission();
    }

    @Named("password")
    default String setPWD(String pwd){
        return "null";
    }

    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "code", target = "code", ignore = true)
    //@Mapping(source = "email", target = "email", ignore = true)
    @Mapping(source = "pwd", target = "pwd", ignore = true)
    @Mapping(source = "ddn", target = "ddn", ignore = true)
    //@Mapping(source = "lieu", target = "lieu", ignore = true)
    //@Mapping(source = "telephone", target = "telephone", ignore = true)
    @Mapping(source = "photo", target = "photo", ignore = true)
    @Mapping(source = "location", target = "location", ignore = true)
    @Mapping(source = "anonymat", target = "anonymat", ignore = true)
    //@Mapping(source = "sexe", target = "sexe", ignore = true)
    @Mapping(source = "status", target = "status", ignore = true)
    @Mapping(source = "isFirstConnexion", target = "isFirstConnexion", ignore = true)
    @Mapping(source = "groupe", target = "groupe", ignore = true)
    void update(UtilisateurDTO dto, @MappingTarget Utilisateur utilisateur);
}
