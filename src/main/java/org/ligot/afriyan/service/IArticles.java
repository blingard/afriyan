package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.ArticlesDTO;
import org.ligot.afriyan.entities.TypeDonne;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IArticles {
    ArticlesDTO save(ArticlesDTO articlesDTO);
    List<ArticlesDTO> getList(TypeDonne typeDonne);
    List<ArticlesDTO> getListActive(TypeDonne typeDonne);
    ArticlesDTO findById(Long id) throws Exception;
    Page<ArticlesDTO> getPage(int lenght, TypeDonne typeDonne);
    void update(ArticlesDTO articlesDTO, Long id);
    void delete(Long id);

    void active(Long id, TypeDonne typeDonne);
}