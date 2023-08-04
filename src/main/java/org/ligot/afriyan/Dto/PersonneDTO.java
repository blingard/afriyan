package org.ligot.afriyan.Dto;

import jakarta.validation.constraints.Email;
import org.ligot.afriyan.entities.Sexe;
import org.ligot.afriyan.entities.Status;

import java.util.Date;


public class PersonneDTO {

    protected Long id;
    protected String code;
    protected String nom;
    protected String prenom;
    protected Date ddn;
    protected String lieu;
    protected String numero_telephone;
    protected String photo;
    protected String location;
    protected String anonymat;
    protected Sexe sexe;
    @Email
    protected String email;
    protected Status status;
    protected boolean isFirstConnexion;
    protected  String pwd;

    public PersonneDTO(Long id, String code, String nom, String prenom, Date ddn, String lieu, String numero_telephone, String photo, String location, String anonymat, Sexe sexe, String email, Status status, String pwd) {
        this.id = id;
        this.code = code;
        this.nom = nom;
        this.prenom = prenom;
        this.ddn = ddn;
        this.lieu = lieu;
        this.numero_telephone = numero_telephone;
        this.photo = photo;
        this.location = location;
        this.anonymat = anonymat;
        this.sexe = sexe;
        this.email = email;
        this.status = status;
        this.pwd = pwd;
    }

    public PersonneDTO() {
    }

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

    public Date getDdn() {
        return ddn;
    }

    public void setDdn(Date ddn) {
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

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public boolean getIsFirstConnexion() {
        return isFirstConnexion;
    }

    public void setIsFirstConnexion(boolean firstConnexion) {
        isFirstConnexion = firstConnexion;
    }
}
