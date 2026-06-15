package org.ligot.afriyan.Dto;

import org.ligot.afriyan.entities.ObjectifPlanning;
import org.ligot.afriyan.entities.MethodePlanning;
import java.io.Serializable;
import java.util.Date;

public class PlanningFamilialDTO implements Serializable {
    private Long id;
    private ObjectifPlanning objectif;
    private MethodePlanning methode;
    private Date dateDebut;
    private boolean rappelActif;
    private String heureRappel;
    private boolean synchroniserRegles;

    public PlanningFamilialDTO() {
    }

    public PlanningFamilialDTO(Long id, ObjectifPlanning objectif, MethodePlanning methode, Date dateDebut, boolean rappelActif, String heureRappel, boolean synchroniserRegles) {
        this.id = id;
        this.objectif = objectif;
        this.methode = methode;
        this.dateDebut = dateDebut;
        this.rappelActif = rappelActif;
        this.heureRappel = heureRappel;
        this.synchroniserRegles = synchroniserRegles;
    }

    public static PlanningFamilialDTOBuilder builder() {
        return new PlanningFamilialDTOBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public static class PlanningFamilialDTOBuilder {
        private Long id;
        private ObjectifPlanning objectif;
        private MethodePlanning methode;
        private Date dateDebut;
        private boolean rappelActif;
        private String heureRappel;
        private boolean synchroniserRegles;

        public PlanningFamilialDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public PlanningFamilialDTOBuilder objectif(ObjectifPlanning objectif) {
            this.objectif = objectif;
            return this;
        }
        public PlanningFamilialDTOBuilder methode(MethodePlanning methode) {
            this.methode = methode;
            return this;
        }
        public PlanningFamilialDTOBuilder dateDebut(Date dateDebut) {
            this.dateDebut = dateDebut;
            return this;
        }
        public PlanningFamilialDTOBuilder rappelActif(boolean rappelActif) {
            this.rappelActif = rappelActif;
            return this;
        }
        public PlanningFamilialDTOBuilder heureRappel(String heureRappel) {
            this.heureRappel = heureRappel;
            return this;
        }
        public PlanningFamilialDTOBuilder synchroniserRegles(boolean synchroniserRegles) {
            this.synchroniserRegles = synchroniserRegles;
            return this;
        }
        public PlanningFamilialDTO build() {
            return new PlanningFamilialDTO(id, objectif, methode, dateDebut, rappelActif, heureRappel, synchroniserRegles);
        }
    }
}
