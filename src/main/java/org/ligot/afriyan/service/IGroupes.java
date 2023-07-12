package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.GroupesDTO;
import org.ligot.afriyan.Dto.RolesDTO;
import org.ligot.afriyan.entities.Roles;
import org.springframework.data.domain.Page;

import java.util.Set;


public interface IGroupes {

    GroupesDTO findById(Long id) throws Exception;
    GroupesDTO save(GroupesDTO groupesDto) throws Exception;
    Page<GroupesDTO> list(int page) throws Exception;
    GroupesDTO update(GroupesDTO groupesDto, Long id) throws Exception;
    void delete(Long id) throws Exception;
    GroupesDTO addRoles (GroupesDTO roles, Long id) throws Exception;

}
