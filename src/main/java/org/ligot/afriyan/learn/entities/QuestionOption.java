package org.ligot.afriyan.learn.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "learn_question_options")
public class QuestionOption implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String texte;

    @Column(name = "is_correct", nullable = false)
    private Boolean isCorrect = false;

    @Column(nullable = false)
    private Integer ordre; // Ordre d'affichage de l'option

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    public QuestionOption() {
    }

    public QuestionOption(UUID id, String texte, Boolean isCorrect, Integer ordre, Question question) {
        this.id = id;
        this.texte = texte;
        this.isCorrect = isCorrect;
        this.ordre = ordre;
        this.question = question;
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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
