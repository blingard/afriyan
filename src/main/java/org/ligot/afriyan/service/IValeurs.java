package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.ValeursDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IValeurs {
    ValeursDTO save(ValeursDTO valeursDTO) throws Exception;
    List<ValeursDTO> getList() throws Exception;
    ValeursDTO findById(Long id) throws Exception;
    Page<ValeursDTO> getPage(int lenght) throws Exception;
    ValeursDTO update(ValeursDTO valeursDTO, Long id) throws Exception;
    void delete(Long id) throws Exception;
}
