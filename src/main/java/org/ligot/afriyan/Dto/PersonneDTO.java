package org.ligot.afriyan.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.ligot.afriyan.entities.Status;

@NoArgsConstructor
@AllArgsConstructor
public abstract class PersonneDTO {

    protected Long id;
    @NotNull
    protected String code;
    @NotNull
    protected String nom;
    @NotNull
    protected String prenom;
    @NotNull
    protected String ddn;
    @NotNull
    protected String lieu;
    @NotNull
    protected String numero_telephone;
    @NotNull
    protected String photo;
    @NotNull
    protected String location;
    @NotNull
    protected String anonymat;
    @NotNull
    protected String sexe;
    @NotNull
    @Email
    protected String email;

    @NotNull
    protected Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDdn() {
        return ddn;
    }

    public void setDdn(String ddn) {
        this.ddn = ddn;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getNumero_telephone() {
        return numero_telephone;
    }

    public void setNumero_telephone(String numero_telephone) {
        this.numero_telephone = numero_telephone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAnonymat() {
        return anonymat;
    }

    public void setAnonymat(String anonymat) {
        this.anonymat = anonymat;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
