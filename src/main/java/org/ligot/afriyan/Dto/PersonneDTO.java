package org.ligot.afriyan.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.ligot.afriyan.entities.Sexe;
import org.ligot.afriyan.entities.Status;

import java.util.Date;
import java.util.List;


public class PersonneDTO {

    protected Long id;
    protected String code;

    @NotNull
    protected String nom;
    @NotNull
    protected String prenom;

    protected Date ddn;
    protected String lieu;
    protected List<String> phoneNumbers;

    @NotNull(message = "numero te telephone null")
    @JsonProperty("numero_telephone")
    @Pattern(regexp = "^6\\d{8}$", message = "Le numéro de téléphone doit contenir 9 chiffres et commencer par 6.")
    protected String telephone;
    protected String photo;
    protected String location;
    protected String anonymat;
    protected Sexe sexe;
    //@Email
    protected String email;
    protected Status status;
    protected boolean isFirstConnexion;
    protected  String pwd;

    public PersonneDTO(Long id, String code, String nom, String prenom, Date ddn, String lieu, String telephone, String photo, String location, String anonymat, Sexe sexe, String email, Status status, String pwd, List<String> phoneNumbers) {
        this.id = id;
        this.code = code;
        this.nom = nom;
        this.prenom = prenom;
        this.ddn = ddn;
        this.lieu = lieu;
        this.telephone = telephone;
        this.photo = photo;
        this.location = location;
        this.anonymat = anonymat;
        this.sexe = sexe;
        this.email = email;
        this.status = status;
        this.pwd = pwd;
        this.phoneNumbers = phoneNumbers;
    }

    public PersonneDTO() {
    }

    public PersonneDTO(Long id) {
        this.id = id;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
