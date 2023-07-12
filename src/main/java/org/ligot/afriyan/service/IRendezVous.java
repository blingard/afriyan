package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.RendezVousDTO;
import org.springframework.data.domain.Page;


public interface IRendezVous {
    RendezVousDTO  findById(Long id);
    RendezVousDTO save(RendezVousDTO rendezVousDto);
    Page<RendezVousDTO> list(int page);
    Page<RendezVousDTO> list(int page, Long idUser);
    RendezVousDTO update(RendezVousDTO rendezVousDto, Long id);
    void delete(Long id);

}
