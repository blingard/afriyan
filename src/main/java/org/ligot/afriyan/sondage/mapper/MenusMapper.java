package org.ligot.afriyan.sondage.mapper;

import org.ligot.afriyan.Dto.MenusDTO;
import org.ligot.afriyan.entities.Menus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MenusMapper {

    Menus toEntity(MenusDTO menusDTO);

    @Mapping(target = "rootMenu", ignore = true)
    MenusDTO toDTO(Menus menus);
    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "name", target = "name", ignore = true)
    @Mapping(source = "status", target = "status", ignore = true)
    @Mapping(source = "frontType", target = "frontType", ignore = true)
    @Mapping(source = "subMenus", target = "subMenus", ignore = true)
    void update(MenusDTO menusDTO, @MappingTarget Menus menus);
}
