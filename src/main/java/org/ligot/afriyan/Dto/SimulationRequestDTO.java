package org.ligot.afriyan.Dto;

import java.io.Serializable;
import java.util.Date;

public class SimulationRequestDTO implements Serializable {
    private Date dateDernieresRegles;
    private int dureeCycleMoyenne;
    private int dureeReglesMoyenne;
    private int nbCycles = 3;

    public SimulationRequestDTO() {
    }

    public SimulationRequestDTO(Date dateDernieresRegles, int dureeCycleMoyenne, int dureeReglesMoyenne, int nbCycles) {
        this.dateDernieresRegles = dateDernieresRegles;
        this.dureeCycleMoyenne = dureeCycleMoyenne;
        this.dureeReglesMoyenne = dureeReglesMoyenne;
        this.nbCycles = nbCycles;
    }

    public static SimulationRequestDTOBuilder builder() {
        return new SimulationRequestDTOBuilder();
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

    public int getNbCycles() {
        return nbCycles;
    }

    public void setNbCycles(int nbCycles) {
        this.nbCycles = nbCycles;
    }

    public static class SimulationRequestDTOBuilder {
        private Date dateDernieresRegles;
        private int dureeCycleMoyenne;
        private int dureeReglesMoyenne;
        private int nbCycles = 3;

        public SimulationRequestDTOBuilder dateDernieresRegles(Date dateDernieresRegles) {
            this.dateDernieresRegles = dateDernieresRegles;
            return this;
        }
        public SimulationRequestDTOBuilder dureeCycleMoyenne(int dureeCycleMoyenne) {
            this.dureeCycleMoyenne = dureeCycleMoyenne;
            return this;
        }
        public SimulationRequestDTOBuilder dureeReglesMoyenne(int dureeReglesMoyenne) {
            this.dureeReglesMoyenne = dureeReglesMoyenne;
            return this;
        }
        public SimulationRequestDTOBuilder nbCycles(int nbCycles) {
            this.nbCycles = nbCycles;
            return this;
        }
        public SimulationRequestDTO build() {
            return new SimulationRequestDTO(dateDernieresRegles, dureeCycleMoyenne, dureeReglesMoyenne, nbCycles);
        }
    }
}
