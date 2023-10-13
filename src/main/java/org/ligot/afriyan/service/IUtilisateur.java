package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.ChangePwd;
import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;


public interface IUtilisateur {

    UtilisateurDTO findById(Long id) throws Exception ;
    UtilisateurDTO save(UtilisateurDTO utilisateurDTO, Long idGroupe) throws Exception ;
    UtilisateurDTO register(UtilisateurDTO utilisateurDTO) throws Exception ;
    Page<UtilisateurDTO> list(int page) throws Exception ;
    List<UtilisateurDTO> list(Long groupId) throws Exception ;
    List<UtilisateurDTO> list() throws Exception ;
    List<UtilisateurDTO> list(String role) throws Exception ;
    UtilisateurDTO update(UtilisateurDTO utilisateurDTO, Long id) throws Exception ;
    void disableUtilisateur(Long id) throws Exception ;

    UtilisateurDTO findByName(String nom) throws Exception;
    UtilisateurDTO findByLogin(String login) throws Exception;

    void changePassword(ChangePwd changePwd) throws Exception;

    Map<String, Object> dashboard() throws Exception;

    void activeOrDesactive(Long id) throws Exception;

    void resetPassword(Long id) throws Exception;
}
