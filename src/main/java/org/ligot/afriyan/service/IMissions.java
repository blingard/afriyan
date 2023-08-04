package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.MissionsDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IMissions {
    MissionsDTO save(MissionsDTO valeursDTO);
    List<MissionsDTO> getList();
    MissionsDTO findById(Long id) throws Exception;
    Page<MissionsDTO> getPage(int lenght);
    MissionsDTO update(MissionsDTO missionsDTO, Long id) throws Exception;
    void delete(Long id);
}
