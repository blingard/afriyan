package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.GroupesDTO;
import org.ligot.afriyan.Dto.RolesDTO;
import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.entities.Roles;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;


public interface IGroupes {

    GroupesDTO findById(Long id) throws Exception;
    GroupesDTO findByName(String name) throws Exception;
    GroupesDTO save(GroupesDTO groupesDto) throws Exception;
    Page<GroupesDTO> list(int page) throws Exception;
    List<GroupesDTO> list() throws Exception;
    GroupesDTO update(GroupesDTO groupesDto, Long id) throws Exception;
    void delete(Long id) throws Exception;
    GroupesDTO addRoles (Set<RolesDTO> roles, Long id) throws Exception;
    void removeRoles (Set<RolesDTO> roles, Long id) throws Exception;
    GroupesDTO addUsers (Set<UtilisateurDTO> utilisateurDTOS, Long id) throws Exception;
    void removeUsers (Set<UtilisateurDTO> utilisateurDTOS, Long id) throws Exception;
    List<RolesDTO> listRoles();
    List<GroupesDTO>  getByRole(String role) throws Exception;

    List<RolesDTO> listGroupRoles(Long id) throws Exception;
}

