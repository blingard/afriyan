package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.DenonciationDTO;
import org.springframework.data.domain.Page;

public interface IDenonciation {
    DenonciationDTO save(DenonciationDTO articlesDTO) throws Exception;
    DenonciationDTO findById(Long id) throws Exception;
    Page<DenonciationDTO> getPage(int lenght) throws Exception;
    DenonciationDTO update(DenonciationDTO denonciationDTO, Long id) throws Exception;
    void delete(Long id) throws Exception;
}
