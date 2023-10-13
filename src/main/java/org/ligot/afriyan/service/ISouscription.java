package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.SouscriptionDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ISouscription {
    SouscriptionDTO findById(Long id) throws Exception;
    List<SouscriptionDTO> findByIdUser(Long id) throws Exception;
    List<SouscriptionDTO> findByIdService(Long id) throws Exception;
    List<SouscriptionDTO> findByIdCP(Long id) throws Exception;
    SouscriptionDTO save(SouscriptionDTO personneDto) throws Exception;
    Page<SouscriptionDTO> list(int page) throws Exception;
    SouscriptionDTO update(SouscriptionDTO personneDto, Long id) throws Exception;
    void delete(Long id) throws Exception;
}
