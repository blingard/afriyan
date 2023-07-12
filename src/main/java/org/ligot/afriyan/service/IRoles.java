package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.RolesDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRoles {

    RolesDTO findById(Long id);
    RolesDTO save(RolesDTO rolesDto);
    Page<RolesDTO> list();
    RolesDTO update(RolesDTO rolesDto, Long id);
    void delete(Long id);

}
