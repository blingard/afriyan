package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.CentrePartenaireDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICentrePartenaire {

    CentrePartenaireDTO findById(Long id) throws Exception;
    CentrePartenaireDTO save(CentrePartenaireDTO centrePartenaireDto);
    Page<CentrePartenaireDTO> list(int page) throws Exception;
    List<CentrePartenaireDTO> list() throws Exception;
    CentrePartenaireDTO update(CentrePartenaireDTO centrePartenaireDto, Long id) throws Exception;
    void delete(Long id) throws Exception;

}
