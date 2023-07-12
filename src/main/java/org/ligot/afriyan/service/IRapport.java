package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.RapportDTO;
import org.springframework.data.domain.Page;


public interface IRapport {

    RapportDTO  findById(Long id);
    RapportDTO save(RapportDTO rapportDto);
    Page<RapportDTO> list(int page);
    RapportDTO update(RapportDTO rapportDto, Long id);
    void delete(Long id);

}
