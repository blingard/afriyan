package org.ligot.afriyan.Dto;

import org.ligot.afriyan.entities.ParamTypeEnum;

public class ParametresDto {
    private Long id;

    private String titre;

    private String valeur;
    private String taille;
    private boolean status;
    private ParamTypeEnum paramTypeEnum;

    public ParametresDto() {

    }

    public ParametresDto(Long id, String titre, String valeur, String taille, boolean status, ParamTypeEnum paramTypeEnum) {
        this.id = id;
        this.titre = titre;
        this.valeur = valeur;
        this.taille = taille;
        this.status = status;
        this.paramTypeEnum = paramTypeEnum;
    }

    public ParamTypeEnum getParamTypeEnum() {
        return paramTypeEnum;
    }

    public void setParamTypeEnum(ParamTypeEnum paramTypeEnum) {
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

    @Override
    public String toString() {
        return "ParametresDto{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", valeur='" + valeur + '\'' +
                ", taille='" + taille + '\'' +
                ", status=" + status +
                ", paramTypeEnum=" + paramTypeEnum +
                '}';
    }
}
