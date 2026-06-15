package org.ligot.afriyan.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "cycle_menstruel_settings")
public class CycleMenstruelSettings implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private Utilisateur utilisateur;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_dernieres_regles", nullable = false)
    private Date dateDernieresRegles;

    @Column(name = "duree_cycle_moyenne", nullable = false)
    private int dureeCycleMoyenne = 28;

    @Column(name = "duree_regles_moyenne", nullable = false)
    private int dureeReglesMoyenne = 5;

    @Column(name = "sms_alerte_active", nullable = false)
    private boolean smsAlerteActive = false;

    @Column(name = "jours_avant_alerte", nullable = false)
    private int joursAvantAlerte = 2;

    public CycleMenstruelSettings() {
    }

    public CycleMenstruelSettings(Long id, Utilisateur utilisateur, Date dateDernieresRegles, int dureeCycleMoyenne, int dureeReglesMoyenne, boolean smsAlerteActive, int joursAvantAlerte) {
        this.id = id;
        this.utilisateur = utilisateur;
        this.dateDernieresRegles = dateDernieresRegles;
        this.dureeCycleMoyenne = dureeCycleMoyenne;
        this.dureeReglesMoyenne = dureeReglesMoyenne;
        this.smsAlerteActive = smsAlerteActive;
        this.joursAvantAlerte = joursAvantAlerte;
    }

    public static CycleMenstruelSettingsBuilder builder() {
        return new CycleMenstruelSettingsBuilder();
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

    public Date getDateDernieresRegles() {
        return dateDernieresRegles;
    }

    public void setDateDernieresRegles(Date dateDernieresRegles) {
        this.dateDernieresRegles = dateDernieresRegles;
    }

    public int getDureeCycleMoyenne() {
        return dureeCycleMoyenne;
    }

    public void setDureeCycleMoyenne(int dureeCycleMoyenne) {
        this.dureeCycleMoyenne = dureeCycleMoyenne;
    }

    public int getDureeReglesMoyenne() {
        return dureeReglesMoyenne;
    }

    public void setDureeReglesMoyenne(int dureeReglesMoyenne) {
        this.dureeReglesMoyenne = dureeReglesMoyenne;
    }

    public boolean isSmsAlerteActive() {
        return smsAlerteActive;
    }

    public void setSmsAlerteActive(boolean smsAlerteActive) {
        this.smsAlerteActive = smsAlerteActive;
    }

    public int getJoursAvantAlerte() {
        return joursAvantAlerte;
    }

    public void setJoursAvantAlerte(int joursAvantAlerte) {
        this.joursAvantAlerte = joursAvantAlerte;
    }

    public static class CycleMenstruelSettingsBuilder {
        private Long id;
        private Utilisateur utilisateur;
        private Date dateDernieresRegles;
        private int dureeCycleMoyenne = 28;
        private int dureeReglesMoyenne = 5;
        private boolean smsAlerteActive = false;
        private int joursAvantAlerte = 2;

        public CycleMenstruelSettingsBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public CycleMenstruelSettingsBuilder utilisateur(Utilisateur utilisateur) {
            this.utilisateur = utilisateur;
            return this;
        }
        public CycleMenstruelSettingsBuilder dateDernieresRegles(Date dateDernieresRegles) {
            this.dateDernieresRegles = dateDernieresRegles;
            return this;
        }
        public CycleMenstruelSettingsBuilder dureeCycleMoyenne(int dureeCycleMoyenne) {
            this.dureeCycleMoyenne = dureeCycleMoyenne;
            return this;
        }
        public CycleMenstruelSettingsBuilder dureeReglesMoyenne(int dureeReglesMoyenne) {
            this.dureeReglesMoyenne = dureeReglesMoyenne;
            return this;
        }
        public CycleMenstruelSettingsBuilder smsAlerteActive(boolean smsAlerteActive) {
            this.smsAlerteActive = smsAlerteActive;
            return this;
        }
        public CycleMenstruelSettingsBuilder joursAvantAlerte(int joursAvantAlerte) {
            this.joursAvantAlerte = joursAvantAlerte;
            return this;
        }
        public CycleMenstruelSettings build() {
            return new CycleMenstruelSettings(id, utilisateur, dateDernieresRegles, dureeCycleMoyenne, dureeReglesMoyenne, smsAlerteActive, joursAvantAlerte);
        }
    }
}
