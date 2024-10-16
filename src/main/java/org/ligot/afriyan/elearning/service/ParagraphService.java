package org.ligot.afriyan.elearning.service;

import org.ligot.afriyan.elearning.dto.ParagraphsDTO;
import org.ligot.afriyan.elearning.entities.Paragraphs;
import org.springframework.web.multipart.MultipartFile;

public interface ParagraphService {
    void save(Long idChapter, ParagraphsDTO paragraphs, MultipartFile file)throws Exception;
    void update(Long id, ParagraphsDTO paragraphsDTO) throws Exception;
    void enable(Long id)throws Exception;
    void disable(Long id)throws Exception;
    ParagraphsDTO getById(Long id)throws Exception;
    ParagraphsDTO getByIdAdmin(Long id)throws Exception;

}
