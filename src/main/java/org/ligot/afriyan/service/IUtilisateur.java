package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.springframework.data.domain.Page;


public interface IUtilisateur {

    UtilisateurDTO findById(Long id);
    UtilisateurDTO save(UtilisateurDTO utilisateurDTO);
    Page<UtilisateurDTO> list(int page);
    Page<UtilisateurDTO> list(int page,Long idGroup);
    UtilisateurDTO update(UtilisateurDTO utilisateurDTO, Long id);
    void deleteUtilisateur(Long id);

}
