package org.ligot.afriyan.learn.dto;

public class QuestionOptionCreateDTO {
    private String texte;

    private Boolean isCorrect;
    private Integer ordre;

    public QuestionOptionCreateDTO() {
    }

    public QuestionOptionCreateDTO(String texte, Boolean isCorrect, Integer ordre) {
        this.texte = texte;
        this.isCorrect = isCorrect;
        this.ordre = ordre;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Integer getOrdre() {
        return ordre;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }
}
