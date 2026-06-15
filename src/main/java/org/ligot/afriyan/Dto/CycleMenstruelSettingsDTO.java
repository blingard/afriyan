package org.ligot.afriyan.Dto;

import java.io.Serializable;
import java.util.Date;

public class CycleMenstruelSettingsDTO implements Serializable {
    private Long id;
    private Date dateDernieresRegles;
    private int dureeCycleMoyenne;
    private int dureeReglesMoyenne;
    private boolean smsAlerteActive;
    private int joursAvantAlerte;

    public CycleMenstruelSettingsDTO() {
    }

    public CycleMenstruelSettingsDTO(Long id, Date dateDernieresRegles, int dureeCycleMoyenne, int dureeReglesMoyenne, boolean smsAlerteActive, int joursAvantAlerte) {
        this.id = id;
        this.dateDernieresRegles = dateDernieresRegles;
        this.dureeCycleMoyenne = dureeCycleMoyenne;
        this.dureeReglesMoyenne = dureeReglesMoyenne;
        this.smsAlerteActive = smsAlerteActive;
        this.joursAvantAlerte = joursAvantAlerte;
    }

    public static CycleMenstruelSettingsDTOBuilder builder() {
        return new CycleMenstruelSettingsDTOBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public static class CycleMenstruelSettingsDTOBuilder {
        private Long id;
        private Date dateDernieresRegles;
        private int dureeCycleMoyenne;
        private int dureeReglesMoyenne;
        private boolean smsAlerteActive;
        private int joursAvantAlerte;

        public CycleMenstruelSettingsDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public CycleMenstruelSettingsDTOBuilder dateDernieresRegles(Date dateDernieresRegles) {
            this.dateDernieresRegles = dateDernieresRegles;
            return this;
        }
        public CycleMenstruelSettingsDTOBuilder dureeCycleMoyenne(int dureeCycleMoyenne) {
            this.dureeCycleMoyenne = dureeCycleMoyenne;
            return this;
        }
        public CycleMenstruelSettingsDTOBuilder dureeReglesMoyenne(int dureeReglesMoyenne) {
            this.dureeReglesMoyenne = dureeReglesMoyenne;
            return this;
        }
        public CycleMenstruelSettingsDTOBuilder smsAlerteActive(boolean smsAlerteActive) {
            this.smsAlerteActive = smsAlerteActive;
            return this;
        }
        public CycleMenstruelSettingsDTOBuilder joursAvantAlerte(int joursAvantAlerte) {
            this.joursAvantAlerte = joursAvantAlerte;
            return this;
        }
        public CycleMenstruelSettingsDTO build() {
            return new CycleMenstruelSettingsDTO(id, dateDernieresRegles, dureeCycleMoyenne, dureeReglesMoyenne, smsAlerteActive, joursAvantAlerte);
        }
    }
}
