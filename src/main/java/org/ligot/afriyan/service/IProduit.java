package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.ProduitDTO;
import org.ligot.afriyan.Dto.ProduitRequest;
import org.springframework.data.domain.Page;

public interface IProduit {

    ProduitDTO findById(Long id) throws Exception;
    ProduitDTO save(ProduitRequest produitRequest) throws Exception;
    Page<ProduitDTO> list(int page) throws Exception;
    ProduitDTO update(ProduitDTO produitDto, Long id) throws Exception;
    void delete(Long id) throws Exception;

}
