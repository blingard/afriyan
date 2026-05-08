package org.ligot.afriyan.learn.dto;

import java.util.UUID;

public class QuestionOptionUserDTO {
    private UUID id;
    private String texte;
    private Integer ordre;

    public QuestionOptionUserDTO() {
    }

    public QuestionOptionUserDTO(UUID id, String texte, Integer ordre) {
        this.id = id;
        this.texte = texte;
        this.ordre = ordre;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public Integer getOrdre() {
        return ordre;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }
}
