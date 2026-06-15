package org.ligot.afriyan.test;

import jakarta.persistence.*;

@Entity
@Table(name = "note")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String eleveId;
    private String matiereId;
    private double note;
    private double coefficient;
    private String appreciation;
    private int trimestre;
    private String anneeScolaire;

    public Note() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEleveId() { return eleveId; }
    public void setEleveId(String eleveId) { this.eleveId = eleveId; }

    public String getMatiereId() { return matiereId; }
    public void setMatiereId(String matiereId) { this.matiereId = matiereId; }

    public double getNote() { return note; }
    public void setNote(double note) { this.note = note; }

    public double getCoefficient() { return coefficient; }
    public void setCoefficient(double coefficient) { this.coefficient = coefficient; }

    public String getAppreciation() { return appreciation; }
    public void setAppreciation(String appreciation) { this.appreciation = appreciation; }

    public int getTrimestre() { return trimestre; }
    public void setTrimestre(int trimestre) { this.trimestre = trimestre; }

    public String getAnneeScolaire() { return anneeScolaire; }
    public void setAnneeScolaire(String anneeScolaire) { this.anneeScolaire = anneeScolaire; }
}
