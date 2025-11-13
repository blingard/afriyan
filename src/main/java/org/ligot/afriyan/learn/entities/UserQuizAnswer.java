package org.ligot.afriyan.learn.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "learn_user_quiz_answers")
public class UserQuizAnswer implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attempt_id", nullable = false)
    private UserQuizAttempt attempt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "selected_option_id")
    private QuestionOption selectedOption;

    @Column(name = "is_correct")
    private Boolean isCorrect = false;

    @Column(name = "points_obtenus")
    private Integer pointsObtenus = 0;

    @CreationTimestamp
    @Column(name = "date_reponse", updatable = false)
    private Date dateReponse;

    public UserQuizAnswer() {
    }

    public UserQuizAnswer(UUID id, UserQuizAttempt attempt, Question question, QuestionOption selectedOption, Boolean isCorrect, Integer pointsObtenus, Date dateReponse) {
        this.id = id;
        this.attempt = attempt;
        this.question = question;
        this.selectedOption = selectedOption;
        this.isCorrect = isCorrect;
        this.pointsObtenus = pointsObtenus;
        this.dateReponse = dateReponse;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserQuizAttempt getAttempt() {
        return attempt;
    }

    public void setAttempt(UserQuizAttempt attempt) {
        this.attempt = attempt;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public QuestionOption getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(QuestionOption selectedOption) {
        this.selectedOption = selectedOption;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean correct) {
        this.isCorrect = correct;
    }

    public Integer getPointsObtenus() {
        return pointsObtenus;
    }

    public void setPointsObtenus(Integer pointsObtenus) {
        this.pointsObtenus = pointsObtenus;
    }

    public Date getDateReponse() {
        return dateReponse;
    }

    public void setDateReponse(Date dateReponse) {
        this.dateReponse = dateReponse;
    }
}
