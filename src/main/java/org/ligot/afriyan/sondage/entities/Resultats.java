package org.ligot.afriyan.sondage.entities;

import jakarta.persistence.*;
import org.ligot.afriyan.elearning.entities.Formations;
import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.sondage.enumerations.TypeResponse;
import org.ligot.afriyan.sondage.enumerations.TypeUserSondage;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "resultats")
public class Resultats {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Float scoreTotal;
    private int score;
    @ManyToOne(fetch = FetchType.EAGER)
    private Formations formation;
    @ManyToOne(fetch = FetchType.EAGER)
    private Utilisateur utilisateur;

    private Date dateTime;

    private boolean status;

    public Resultats() {
    }

    public Resultats(Long id, Float scoreTotal, int score, Formations formation, Utilisateur utilisateur, Date dateTime, boolean status) {
        this.id = id;
        this.scoreTotal = scoreTotal;
        this.score = score;
        this.formation = formation;
        this.utilisateur = utilisateur;
        this.dateTime = dateTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(Float scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Formations getFormation() {
        return formation;
    }

    public void setFormation(Formations formation) {
        this.formation = formation;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
