package org.ligot.afriyan.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "period_logs")
public class PeriodLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Utilisateur utilisateur;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_debut", nullable = false)
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_fin")
    private Date dateFin;

    public PeriodLog() {
    }

    public PeriodLog(Long id, Utilisateur utilisateur, Date dateDebut, Date dateFin) {
        this.id = id;
        this.utilisateur = utilisateur;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public static PeriodLogBuilder builder() {
        return new PeriodLogBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
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

    public static class PeriodLogBuilder {
        private Long id;
        private Utilisateur utilisateur;
        private Date dateDebut;
        private Date dateFin;

        public PeriodLogBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public PeriodLogBuilder utilisateur(Utilisateur utilisateur) {
            this.utilisateur = utilisateur;
            return this;
        }
        public PeriodLogBuilder dateDebut(Date dateDebut) {
            this.dateDebut = dateDebut;
            return this;
        }
        public PeriodLogBuilder dateFin(Date dateFin) {
            this.dateFin = dateFin;
            return this;
        }
        public PeriodLog build() {
            return new PeriodLog(id, utilisateur, dateDebut, dateFin);
        }
    }
}
