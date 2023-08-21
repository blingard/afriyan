package org.ligot.afriyan.entities;

import jakarta.persistence.*;

@Entity
public class Parametres {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, nullable = false)
    private String titre;

    @Column(nullable = false)
    private String valeur;
    private String taille;
    private boolean status;

    @Enumerated(EnumType.STRING)
    private ParamTypeEnum paramTypeEnum;

    public Parametres() {
    }

    public Parametres(Long id, String titre, String valeur, String taille, boolean status, ParamTypeEnum paramTypeEnum) {
        this.id = id;
        this.titre = titre;
        this.valeur = valeur;
        this.taille = taille;
        this.status = status;
        this.paramTypeEnum = paramTypeEnum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public String getTaille() {
        return taille;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ParamTypeEnum getParamTypeEnum() {
        return paramTypeEnum;
    }

    public void setParamTypeEnum(ParamTypeEnum paramTypeEnum) {
        this.paramTypeEnum = paramTypeEnum;
    }
}
