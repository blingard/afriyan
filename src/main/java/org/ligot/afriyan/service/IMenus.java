package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.MenusDTO;
import org.ligot.afriyan.Dto.PageDTO;
import org.ligot.afriyan.entities.Categories;
import org.ligot.afriyan.entities.FrontType;

public interface IMenus {
    void save(MenusDTO menusDTO);
    void init(Categories categories);
    PageDTO<MenusDTO> getAllRootMenu(int page);
    MenusDTO listAllMenuActivePath(FrontType frontType);
    MenusDTO findById(String id);
    void update(String code, String description, boolean status);
}
