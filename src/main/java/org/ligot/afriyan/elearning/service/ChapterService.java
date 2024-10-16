package org.ligot.afriyan.elearning.service;

import org.ligot.afriyan.elearning.dto.ChapitresDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ChapterService {
    void save(Long idFormation, ChapitresDTO chapitresDTO)throws Exception;
    void update(Long id, ChapitresDTO chapitresDTO) throws Exception;
    void enable(Long id)throws Exception;
    void disable(Long id)throws Exception;
    ChapitresDTO getById(Long id)throws Exception;
    ChapitresDTO getByIdAdmin(Long id)throws Exception;
}
