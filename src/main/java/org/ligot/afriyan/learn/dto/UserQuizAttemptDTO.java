package org.ligot.afriyan.learn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UserQuizAttemptDTO {
    private UUID id;
    private Long userId;
    private UUID quizId;
    private String quizTitre;
    private UUID enrollmentId;
    private Integer numeroTentative;
    private Double scoreObtenu;
    private Integer pointsObtenus;
    private Integer pointsTotaux;
    private Boolean isPassed;
    private Boolean isCompleted;
    private Date dateDebut;
    private Date dateFin;
    private Integer dureeSecondes;
    private List<UserQuizAnswerDTO> answers;

    public UserQuizAttemptDTO() {
    }

    public UserQuizAttemptDTO(UUID id, Long userId, UUID quizId, String quizTitre, UUID enrollmentId, Integer numeroTentative, Double scoreObtenu, Integer pointsObtenus, Integer pointsTotaux, Boolean isPassed, Boolean isCompleted, Date dateDebut, Date dateFin, Integer dureeSecondes, List<UserQuizAnswerDTO> answers) {
        this.id = id;
        this.userId = userId;
        this.quizId = quizId;
        this.quizTitre = quizTitre;
        this.enrollmentId = enrollmentId;
        this.numeroTentative = numeroTentative;
        this.scoreObtenu = scoreObtenu;
        this.pointsObtenus = pointsObtenus;
        this.pointsTotaux = pointsTotaux;
        this.isPassed = isPassed;
        this.isCompleted = isCompleted;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.dureeSecondes = dureeSecondes;
        this.answers = answers;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UUID getQuizId() {
        return quizId;
    }

    public void setQuizId(UUID quizId) {
        this.quizId = quizId;
    }

    public String getQuizTitre() {
        return quizTitre;
    }

    public void setQuizTitre(String quizTitre) {
        this.quizTitre = quizTitre;
    }

    public UUID getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(UUID enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Integer getNumeroTentative() {
        return numeroTentative;
    }

    public void setNumeroTentative(Integer numeroTentative) {
        this.numeroTentative = numeroTentative;
    }

    public Double getScoreObtenu() {
        return scoreObtenu;
    }

    public void setScoreObtenu(Double scoreObtenu) {
        this.scoreObtenu = scoreObtenu;
    }

    public Integer getPointsObtenus() {
        return pointsObtenus;
    }

    public void setPointsObtenus(Integer pointsObtenus) {
        this.pointsObtenus = pointsObtenus;
    }

    public Integer getPointsTotaux() {
        return pointsTotaux;
    }

    public void setPointsTotaux(Integer pointsTotaux) {
        this.pointsTotaux = pointsTotaux;
    }

    public Boolean getPassed() {
        return isPassed;
    }

    public void setPassed(Boolean passed) {
        isPassed = passed;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Integer getDureeSecondes() {
        return dureeSecondes;
    }

    public void setDureeSecondes(Integer dureeSecondes) {
        this.dureeSecondes = dureeSecondes;
    }

    public List<UserQuizAnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<UserQuizAnswerDTO> answers) {
        this.answers = answers;
    }
}
