package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.ProduitDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProduit {

    ProduitDTO findById(Long id);
    ProduitDTO save(ProduitDTO produitDto);
    Page<ProduitDTO> list(int page);
    ProduitDTO update(ProduitDTO produitDto, Long id);
    void delete(Long id);

}
