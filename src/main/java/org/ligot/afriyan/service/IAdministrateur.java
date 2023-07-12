package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.AdministrationDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IAdministrateur {

    AdministrationDTO findById(Long id);
    AdministrationDTO save(AdministrationDTO personneDto);
    Page<AdministrationDTO> list(int page);
    AdministrationDTO update(AdministrationDTO personneDto, Long id);
    void delete(Long id);

}
