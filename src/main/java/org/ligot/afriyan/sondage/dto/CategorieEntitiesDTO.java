package org.ligot.afriyan.sondage.dto;

import org.ligot.afriyan.Dto.CategoriesDTO;

import java.io.Serializable;

public class CategorieEntitiesDTO {
    private Long id;

    private CategoriesDTO domain;

    public CategorieEntitiesDTO() {
    }

    public CategorieEntitiesDTO(Long id, CategoriesDTO domain) {
        this.id = id;
        this.domain = domain;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoriesDTO getDomain() {
        return domain;
    }

    public void setDomain(CategoriesDTO domain) {
        this.domain = domain;
    }
}
