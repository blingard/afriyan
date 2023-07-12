package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.ProduitDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProduit {

    ProduitDTO findById(Long id) throws Exception;
    ProduitDTO save(ProduitDTO produitDto) throws Exception;
    Page<ProduitDTO> list(int page) throws Exception;
    ProduitDTO update(ProduitDTO produitDto, Long id) throws Exception;
    void delete(Long id) throws Exception;

}
