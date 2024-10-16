package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.ArticlesDTO;
import org.ligot.afriyan.Dto.ThemeTraiterDTO;
import org.ligot.afriyan.entities.Categorie;
import org.ligot.afriyan.entities.TypeDonne;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IThemeTraiter {
    ThemeTraiterDTO save(ThemeTraiterDTO themeTraiterDTO);
    List<ThemeTraiterDTO> getListActive();
    List<ThemeTraiterDTO> getList();
    ThemeTraiterDTO findById(Long id) throws Exception;
    ThemeTraiterDTO update(ThemeTraiterDTO themeTraiterDTO, Long id) throws Exception;
    void delete(Long id);

    void active(Long id);
}
