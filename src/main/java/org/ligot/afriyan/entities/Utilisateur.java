package org.ligot.afriyan.entities;

import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "code"),
                @UniqueConstraint(columnNames = "email")
        })
public class Utilisateur implements Serializable, Comparable<Utilisateur> {


    @Id
    @Column(name = "IDENTIFIANT")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name="CODE", nullable = false, unique = true)
    protected String code;

    @Column(name = "NOM")
    protected String nom;

    @Column(name = "PRENOM")
    protected String prenom;

    @Column(name = "DATE_NAISSANCE")
    protected Date ddn;

    @Column(name = "LIEU_NAISSANCE")
    protected String lieu;

    @Column(name = "NUMERO_TELEPHONE", unique = true)
    protected String numero_telephone;

    @Column(name = "PHOTO")
    protected String photo;

    @Column(name = "LOCATION")
    protected String location;

    @Column(name="ANONYMAT")
    protected String anonymat;

    @Column(name = "SEXE")
    @Enumerated(EnumType.STRING)
    protected Sexe sexe;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    protected Status status = Status.ACTIVE;

    @Column(name = "EMAIL")
    @Email(message = "Veuillez saisir une adresse mail")
    @NotBlank
    @Size(max = 50)
    protected String email;

    @Column(name="PASSWORD", nullable = false)
    protected String pwd;

    @Column(name = "DATECREATION")
    protected Date dCreation;

    @ManyToOne
    private Groupes groupe;
    @Override
    public int compareTo(Utilisateur o) {
        return 0;
    }

    public Groupes getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupes groupe) {
        this.groupe = groupe;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Date getdCreation() {
        return dCreation;
    }

    public void setdCreation(Date dCreation) {
        this.dCreation = dCreation;
    }
}
