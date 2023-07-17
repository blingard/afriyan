package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.springframework.data.domain.Page;


public interface IUtilisateur {

    UtilisateurDTO findById(Long id) throws Exception ;
    UtilisateurDTO save(UtilisateurDTO utilisateurDTO, Long idGroupe) throws Exception ;
    UtilisateurDTO register(UtilisateurDTO utilisateurDTO) throws Exception ;
    Page<UtilisateurDTO> list(int page) throws Exception ;
    Page<UtilisateurDTO> list(int page,Long idGroup) throws Exception ;
    UtilisateurDTO update(UtilisateurDTO utilisateurDTO, Long id) throws Exception ;
    void disableUtilisateur(Long id) throws Exception ;

}
