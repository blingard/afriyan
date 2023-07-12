package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.GroupesDTO;
import org.springframework.data.domain.Page;


public interface IGroupes {

    GroupesDTO findById(Long id);
    GroupesDTO save(GroupesDTO groupesDto);
    Page<GroupesDTO> list(int page);
    GroupesDTO update(GroupesDTO groupesDto, Long id);
    void delete(Long id);

}
