package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.PublicationsDTO;
import org.springframework.data.domain.Page;


public interface IPublications {

    PublicationsDTO findById(Long id);
    PublicationsDTO save(PublicationsDTO publicationsDto);
    Page<PublicationsDTO> list();
    PublicationsDTO update(PublicationsDTO publicationsDto, Long id);
    void delete(Long id);

}
