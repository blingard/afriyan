package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.SouscriptionDTO;
import org.springframework.data.domain.Page;

public interface ISouscription {
    SouscriptionDTO findById(Long id);
    SouscriptionDTO save(SouscriptionDTO personneDto);
    Page<SouscriptionDTO> list(int page);
    SouscriptionDTO update(SouscriptionDTO personneDto, Long id);
    void delete(Long id);
}
