package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.RendezVousDTO;
import org.springframework.data.domain.Page;


public interface IRendezVous {
    RendezVousDTO  findById(Long id) throws Exception;
    RendezVousDTO save(RendezVousDTO rendezVousDto) throws Exception;
    Page<RendezVousDTO> list(int page);
    Page<RendezVousDTO> list(int page, Long idUser) throws Exception;
    RendezVousDTO update(RendezVousDTO rendezVousDto, Long id) throws Exception;
    void delete(Long id) throws Exception;

}
