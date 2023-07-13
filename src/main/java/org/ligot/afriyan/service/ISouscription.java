package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.SouscriptionDTO;
import org.springframework.data.domain.Page;

public interface ISouscription {
    SouscriptionDTO findById(Long id) throws Exception;
    SouscriptionDTO save(SouscriptionDTO personneDto) throws Exception;
    Page<SouscriptionDTO> list(int page) throws Exception;
    SouscriptionDTO update(SouscriptionDTO personneDto, Long id);
    void delete(Long id) throws Exception;
}
