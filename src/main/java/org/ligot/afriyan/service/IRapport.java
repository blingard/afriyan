package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.RapportDTO;
import org.springframework.data.domain.Page;


public interface IRapport {

    RapportDTO  findById(Long id) throws Exception;
    RapportDTO save(RapportDTO rapportDto) throws Exception;
    Page<RapportDTO> list(int page);
    RapportDTO update(RapportDTO rapportDto, Long id) throws Exception;
    void delete(Long id) throws Exception;

}
