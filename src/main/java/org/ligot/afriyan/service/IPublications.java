package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.PublicationsDTO;
import org.springframework.data.domain.Page;


public interface IPublications {

    PublicationsDTO findById(Long id) throws Exception;
    PublicationsDTO save(PublicationsDTO publicationsDto) throws Exception;
    Page<PublicationsDTO> list(int page);
    PublicationsDTO update(PublicationsDTO publicationsDto, Long id) throws Exception;
    void delete(Long id) throws Exception;

}
