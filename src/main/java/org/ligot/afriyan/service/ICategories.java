package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.CategoriesDTO;
import org.ligot.afriyan.Dto.PageDTO;
import org.ligot.afriyan.entities.Categories;

import java.util.List;
import java.util.UUID;

public interface ICategories {
    void save(CategoriesDTO categoriesDTO);
    void init();
    PageDTO<CategoriesDTO> getListAll(int page);
    List<CategoriesDTO> listAllActive();
    List<CategoriesDTO> listAll();
    CategoriesDTO findById(String id);
    void update(CategoriesDTO categoriesDTO, String id);
    void active(String id);

    Categories findCategoriesByMenuId(String menuId);

    Categories findCategoriesById(UUID id);


}
