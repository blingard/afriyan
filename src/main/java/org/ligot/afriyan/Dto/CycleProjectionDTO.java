package org.ligot.afriyan.Dto;

import java.io.Serializable;
import java.util.Date;

public class CycleProjectionDTO implements Serializable {
    private Date dateDebutRegles;
    private Date dateFinRegles;
    private Date dateOvulation;
    private Date debutPeriodeFertile;
    private Date finPeriodeFertile;
    private String phaseActuelle;

    public CycleProjectionDTO() {
    }

    public CycleProjectionDTO(Date dateDebutRegles, Date dateFinRegles, Date dateOvulation, Date debutPeriodeFertile, Date finPeriodeFertile, String phaseActuelle) {
        this.dateDebutRegles = dateDebutRegles;
        this.dateFinRegles = dateFinRegles;
        this.dateOvulation = dateOvulation;
        this.debutPeriodeFertile = debutPeriodeFertile;
        this.finPeriodeFertile = finPeriodeFertile;
        this.phaseActuelle = phaseActuelle;
    }

    public static CycleProjectionDTOBuilder builder() {
        return new CycleProjectionDTOBuilder();
    }

    public Date getDateDebutRegles() {
        return dateDebutRegles;
    }

    public void setDateDebutRegles(Date dateDebutRegles) {
        this.dateDebutRegles = dateDebutRegles;
    }

    public Date getDateFinRegles() {
        return dateFinRegles;
    }

    public void setDateFinRegles(Date dateFinRegles) {
        this.dateFinRegles = dateFinRegles;
    }

    public Date getDateOvulation() {
        return dateOvulation;
    }

    public void setDateOvulation(Date dateOvulation) {
        this.dateOvulation = dateOvulation;
    }

    public Date getDebutPeriodeFertile() {
        return debutPeriodeFertile;
    }

    public void setDebutPeriodeFertile(Date debutPeriodeFertile) {
        this.debutPeriodeFertile = debutPeriodeFertile;
    }

    public Date getFinPeriodeFertile() {
        return finPeriodeFertile;
    }

    public void setFinPeriodeFertile(Date finPeriodeFertile) {
        this.finPeriodeFertile = finPeriodeFertile;
    }

    public String getPhaseActuelle() {
        return phaseActuelle;
    }

    public void setPhaseActuelle(String phaseActuelle) {
        this.phaseActuelle = phaseActuelle;
    }

    public static class CycleProjectionDTOBuilder {
        private Date dateDebutRegles;
        private Date dateFinRegles;
        private Date dateOvulation;
        private Date debutPeriodeFertile;
        private Date finPeriodeFertile;
        private String phaseActuelle;

        public CycleProjectionDTOBuilder dateDebutRegles(Date dateDebutRegles) {
            this.dateDebutRegles = dateDebutRegles;
            return this;
        }
        public CycleProjectionDTOBuilder dateFinRegles(Date dateFinRegles) {
            this.dateFinRegles = dateFinRegles;
            return this;
        }
        public CycleProjectionDTOBuilder dateOvulation(Date dateOvulation) {
            this.dateOvulation = dateOvulation;
            return this;
        }
        public CycleProjectionDTOBuilder debutPeriodeFertile(Date debutPeriodeFertile) {
            this.debutPeriodeFertile = debutPeriodeFertile;
            return this;
        }
        public CycleProjectionDTOBuilder finPeriodeFertile(Date finPeriodeFertile) {
            this.finPeriodeFertile = finPeriodeFertile;
            return this;
        }
        public CycleProjectionDTOBuilder phaseActuelle(String phaseActuelle) {
            this.phaseActuelle = phaseActuelle;
            return this;
        }
        public CycleProjectionDTO build() {
            return new CycleProjectionDTO(dateDebutRegles, dateFinRegles, dateOvulation, debutPeriodeFertile, finPeriodeFertile, phaseActuelle);
        }
    }
}
