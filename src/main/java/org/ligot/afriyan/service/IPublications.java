package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.PublicationsDto;
import org.ligot.afriyan.entities.Publications;

import java.util.List;

public interface IPublications {

    Publications savePublications(PublicationsDto publicationsDto);
    List<Publications> listPublications();
    Publications updatePublications(PublicationsDto publicationsDto, long id);
    void deletePublications(long id);

}
