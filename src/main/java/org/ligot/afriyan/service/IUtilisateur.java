package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.*;
import org.ligot.afriyan.echo.dto.CommuneComityDTO;
import org.ligot.afriyan.echo.dto.CrppDTO;
import org.ligot.afriyan.echo.dto.MaireDTO;
import org.ligot.afriyan.echo.dto.PrefetDTO;
import org.ligot.afriyan.entities.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


public interface IUtilisateur {
    UtilisateurDTO findById(Long id) throws Exception ;
    UtilisateurDTO save(UtilisateurDTO utilisateurDTO, Long idGroupe) throws Exception ;
    void saveUserFile(MultipartFile file) throws Exception ;
    UtilisateurDTO saveAdmin(UtilisateurDTO utilisateurDTO, Long idGroupe) throws Exception ;
    PrefetDTO savePrefet(PrefetDTO prefetDTO) throws Exception ;
    MaireDTO saveMaire(MaireDTO maireDTO) throws Exception;
    void saveCrpp(CrppDTO crppDTO) throws Exception;
    void saveCc(CommuneComityDTO communeComityDTO) throws Exception;
    List<UtilisateurDTO> getUserCP(String name) throws Exception ;
    UtilisateurDTO register(UtilisateurDTO utilisateurDTO) throws Exception ;
    Page<UtilisateurDTO> list(int page) throws Exception ;
    PageDTO<UtilisateurDTO> list(Long groupId, int page) throws Exception ;
    PageDTO<UtilisateurDTO> search(String phone, int page) throws Exception ;
    List<UtilisateurDTO> list() throws Exception ;
    List<UtilisateurDTO> list(String role) throws Exception ;
    void update(UpdateUserDTO updateUserDTO, Long id) throws Exception ;
    UtilisateurDTO update(UtilisateurDTO utilisateurDTO, Long id) throws Exception ;
    String update(MultipartFile file, Long id) throws Exception;
    void disableUtilisateur(Long id) throws Exception ;
    UtilisateurDTO findByName(String nom) throws Exception;
    UtilisateurDTO findByLogin(String login) throws Exception;
    UtilisateurDTO login(String login) throws Exception;
    Utilisateur loginForgetPwd(String login) throws Exception;
    void changePassword(ChangePwd changePwd) throws Exception;
    Map<String, Object> dashboard() throws Exception;
    Map<String, Integer> statusListSave() throws Exception;
    void activeOrDesactive(Long id) throws Exception;
    void resetPassword(Long id) throws Exception;
    void forgetPassword(ForgetPasswordRequest forgetPasswordRequest) throws Exception;
    List<StatistiqueMensuelleUtilisateur> getMonthlyCreationStats();
    Map<String, Object> getData();
}
