package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.ValeursDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IValeurs {
    ValeursDTO save(ValeursDTO valeursDTO);
    List<ValeursDTO> getList();
    List<ValeursDTO> getListActive();
    ValeursDTO findById(Long id) throws Exception;
    Page<ValeursDTO> getPage(int lenght);
    void update(ValeursDTO valeursDTO, Long id) throws Exception;
    void delete(Long id);

    void active(Long id);
}
