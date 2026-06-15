package org.ligot.afriyan.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "planning_familial")
public class PlanningFamilial implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private Utilisateur utilisateur;

    @Enumerated(EnumType.STRING)
    @Column(name = "objectif", nullable = false)
    private ObjectifPlanning objectif;

    @Enumerated(EnumType.STRING)
    @Column(name = "methode", nullable = false)
    private MethodePlanning methode;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_debut", nullable = false)
    private Date dateDebut;

    @Column(name = "rappel_actif", nullable = false)
    private boolean rappelActif = false;

    @Column(name = "heure_rappel")
    private String heureRappel;

    @Column(name = "synchroniser_regles", nullable = false)
    private boolean synchroniserRegles = false;

    public PlanningFamilial() {
    }

    public PlanningFamilial(Long id, Utilisateur utilisateur, ObjectifPlanning objectif, MethodePlanning methode, Date dateDebut, boolean rappelActif, String heureRappel, boolean synchroniserRegles) {
        this.id = id;
        this.utilisateur = utilisateur;
        this.objectif = objectif;
        this.methode = methode;
        this.dateDebut = dateDebut;
        this.rappelActif = rappelActif;
        this.heureRappel = heureRappel;
        this.synchroniserRegles = synchroniserRegles;
    }

    public static PlanningFamilialBuilder builder() {
        return new PlanningFamilialBuilder();
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

    public ObjectifPlanning getObjectif() {
        return objectif;
    }

    public void setObjectif(ObjectifPlanning objectif) {
        this.objectif = objectif;
    }

    public MethodePlanning getMethode() {
        return methode;
    }

    public void setMethode(MethodePlanning methode) {
        this.methode = methode;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public boolean isRappelActif() {
        return rappelActif;
    }

    public void setRappelActif(boolean rappelActif) {
        this.rappelActif = rappelActif;
    }

    public String getHeureRappel() {
        return heureRappel;
    }

    public void setHeureRappel(String heureRappel) {
        this.heureRappel = heureRappel;
    }

    public boolean isSynchroniserRegles() {
        return synchroniserRegles;
    }

    public void setSynchroniserRegles(boolean synchroniserRegles) {
        this.synchroniserRegles = synchroniserRegles;
    }

    public static class PlanningFamilialBuilder {
        private Long id;
        private Utilisateur utilisateur;
        private ObjectifPlanning objectif;
        private MethodePlanning methode;
        private Date dateDebut;
        private boolean rappelActif = false;
        private String heureRappel;
        private boolean synchroniserRegles = false;

        public PlanningFamilialBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public PlanningFamilialBuilder utilisateur(Utilisateur utilisateur) {
            this.utilisateur = utilisateur;
            return this;
        }
        public PlanningFamilialBuilder objectif(ObjectifPlanning objectif) {
            this.objectif = objectif;
            return this;
        }
        public PlanningFamilialBuilder methode(MethodePlanning methode) {
            this.methode = methode;
            return this;
        }
        public PlanningFamilialBuilder dateDebut(Date dateDebut) {
            this.dateDebut = dateDebut;
            return this;
        }
        public PlanningFamilialBuilder rappelActif(boolean rappelActif) {
            this.rappelActif = rappelActif;
            return this;
        }
        public PlanningFamilialBuilder heureRappel(String heureRappel) {
            this.heureRappel = heureRappel;
            return this;
        }
        public PlanningFamilialBuilder synchroniserRegles(boolean synchroniserRegles) {
            this.synchroniserRegles = synchroniserRegles;
            return this;
        }
        public PlanningFamilial build() {
            return new PlanningFamilial(id, utilisateur, objectif, methode, dateDebut, rappelActif, heureRappel, synchroniserRegles);
        }
    }
}
