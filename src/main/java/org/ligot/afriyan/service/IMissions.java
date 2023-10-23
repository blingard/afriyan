package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.MissionsDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IMissions {
    MissionsDTO saveM(MissionsDTO valeursDTO);
    List<MissionsDTO> getListAll();
    List<MissionsDTO> getList();
    MissionsDTO findById(Long id) throws Exception;
    Page<MissionsDTO> getPage(int lenght);
    MissionsDTO update(MissionsDTO missionsDTO, Long id) throws Exception;
    void delete(Long id);

    void active(Long id);

    List<MissionsDTO> getListActive();
    List<MissionsDTO> getList4Active();
}
