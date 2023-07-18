package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.OurWorksDTO;
import org.ligot.afriyan.entities.OurWorksType;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOurWorks {
    OurWorksDTO save(OurWorksDTO valeursDTO);
    List<OurWorksDTO> getList(OurWorksType ourWorksType);
    OurWorksDTO findById(Long id) throws Exception;
    Page<OurWorksDTO> getPage(int lenght);
    void update(OurWorksDTO ourWorksDTO, Long id);
    void delete(Long id);
}
