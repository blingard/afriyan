package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.ArticlesDTO;
import org.ligot.afriyan.entities.TypeDonne;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IArticles {
    ArticlesDTO save(MultipartFile file, ArticlesDTO articlesDTO) throws Exception;
    void updateFile(MultipartFile file, Long id) throws Exception;
    ArticlesDTO save(ArticlesDTO articlesDTO) throws Exception;
    List<ArticlesDTO> getList(TypeDonne typeDonne);
    List<ArticlesDTO> getList(TypeDonne typeDonne, String menuId);
    List<ArticlesDTO> getListAdmin(TypeDonne typeDonne, String categorieId);
    List<ArticlesDTO> getListActive(TypeDonne typeDonne);
    ArticlesDTO findById(Long id) throws Exception;
    ArticlesDTO findByIdActive(Long id) throws Exception;
    Page<ArticlesDTO> getPage(int lenght, TypeDonne typeDonne);
    ArticlesDTO update(ArticlesDTO articlesDTO, Long id) throws Exception;
    void delete(Long id);
    List<ArticlesDTO> get6TopDesc(TypeDonne typeDonne);

    void active(Long id, TypeDonne typeDonne);
}
