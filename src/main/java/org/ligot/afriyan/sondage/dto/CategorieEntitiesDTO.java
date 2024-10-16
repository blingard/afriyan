package org.ligot.afriyan.sondage.dto;

import org.ligot.afriyan.entities.Categorie;

import java.io.Serializable;

public class CategorieEntitiesDTO {
    private Long id;

    private Categorie domain;

    public CategorieEntitiesDTO() {
    }

    public CategorieEntitiesDTO(Long id, Categorie domain) {
        this.id = id;
        this.domain = domain;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Categorie getDomain() {
        return domain;
    }

    public void setDomain(Categorie domain) {
        this.domain = domain;
    }
}
