package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.CentrePartenaireDTO;
import org.ligot.afriyan.Dto.PageDTO;
import org.ligot.afriyan.Dto.SlidersDTO;
import org.ligot.afriyan.Dto.SlidersSmartDTO;
import org.ligot.afriyan.entities.FrontType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ISliders {
    void save(MultipartFile file, SlidersDTO slidersDTO) throws Exception;
    PageDTO<SlidersDTO> getListAll(int page);
    List<SlidersDTO> getListAll();
    SlidersDTO findById(Long id) throws Exception;
    List<SlidersDTO> findToUse() throws Exception;
    List<SlidersSmartDTO> findToUse(FrontType frontType) throws Exception;
    void update(SlidersDTO certificatesDTO, Long id) throws Exception;
    void active(Long id) throws Exception;

    void updateFileSlide(MultipartFile file, Long id) throws Exception;
}
