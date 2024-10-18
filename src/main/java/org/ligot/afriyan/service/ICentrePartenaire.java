package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.CentrePartenaireDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICentrePartenaire {

    CentrePartenaireDTO findById(Long id) throws Exception;
    CentrePartenaireDTO save(MultipartFile file, CentrePartenaireDTO centrePartenaireDto) throws Exception;
    Page<CentrePartenaireDTO> list(int page) throws Exception;
    List<CentrePartenaireDTO> list() throws Exception;
    List<CentrePartenaireDTO> listAll() throws Exception;
    CentrePartenaireDTO update(CentrePartenaireDTO centrePartenaireDto, Long id) throws Exception;
    void delete(Long id) throws Exception;

    CentrePartenaireDTO findByUserId(Long id) throws Exception;

    void active(Long id);
}
