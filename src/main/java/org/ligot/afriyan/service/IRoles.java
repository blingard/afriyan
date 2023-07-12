package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.RolesDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRoles {

    RolesDTO findById(Long id) throws Exception;
    RolesDTO save(RolesDTO rolesDto) throws Exception;
    Page<RolesDTO> list(int page) throws Exception;
    List<RolesDTO> list() throws Exception;
    RolesDTO update(RolesDTO rolesDto, Long id) throws Exception;
    void delete(Long id) throws Exception;

}
