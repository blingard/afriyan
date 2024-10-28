package org.ligot.afriyan.Dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

public class AProposDTO {
    private Long id;

    @NotNull
    private String contenu;

    public AProposDTO() {
    }

    public AProposDTO(Long id, String contenu) {
        this.id = id;
        this.contenu = contenu;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
}
