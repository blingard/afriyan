package org.ligot.afriyan.elearning.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "formations_quizz")
public class FormationsQuizz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long quizzId;

    @Column(nullable = false)
    private Long formationId;

    private boolean finish;
    public FormationsQuizz() {
    }

    public FormationsQuizz(Long id, Long quizzId, Long formationId, boolean finish) {
        this.id = id;
        this.quizzId = quizzId;
        this.formationId = formationId;
        this.finish = finish;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuizzId() {
        return quizzId;
    }

    public void setQuizzId(Long quizzId) {
        this.quizzId = quizzId;
    }

    public Long getFormationId() {
        return formationId;
    }

    public void setFormationId(Long formationId) {
        this.formationId = formationId;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }
}
