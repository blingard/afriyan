package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.CentrePartenaireDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICentrePartenaire {

    CentrePartenaireDTO findById(Long id);
    CentrePartenaireDTO save(CentrePartenaireDTO centrePartenaireDto);
    Page<CentrePartenaireDTO> list(int page);
    CentrePartenaireDTO update(CentrePartenaireDTO centrePartenaireDto, Long id);
    void delete(Long id);

}
