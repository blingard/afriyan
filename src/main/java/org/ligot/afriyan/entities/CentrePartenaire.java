package org.ligot.afriyan.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CentrePartenaire {

     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     @Column(name = "IDENTIFIANT")
     private Long id;
     @Column(name = "NOM")
     private String nom;
     @Column(name = "LIBELLE")
     private String libelle;
     @Column(name = "DESCRIPTION")
     private String description;
     @Column(name = "longitude", nullable = false)
     private String longitude;
     @Column(name = "LATITTUDE", nullable = false)
     private String latittude;
     @Column(name = "LOCATION", nullable = false)
     private String location;
     @Column(name = "TELEPHONE", nullable = false, unique = true)
     private String telephone;
     @Column(name = "ADRESSE")
     private String adresse;
     @Column(name = "FIXE")
     private String fixe;
     @Column(name = "BOITE_POSTALE")
     private String bp;
     @Column(name = "TYPE_CENTRE")
     private String type;
     @Column(name = "NOM_COMMUNE")
     private String nomCommune;
     @Column(name = "DATECREAATION")
     private Date dateCreation;
     @Enumerated(EnumType.STRING)
     private Status status;

     public Long getId() {
          return id;
     }

     public void setId(Long id) {
          this.id = id;
     }

     public String getNom() {
          return nom;
     }

     public void setNom(String nom) {
          this.nom = nom;
     }

     public String getLibelle() {
          return libelle;
     }

     public void setLibelle(String libelle) {
          this.libelle = libelle;
     }

     public String getDescription() {
          return description;
     }

     public void setDescription(String description) {
          this.description = description;
     }

     public String getLongitude() {
          return longitude;
     }

     public void setLongitude(String longitude) {
          this.longitude = longitude;
     }

     public String getLatittude() {
          return latittude;
     }

     public void setLatittude(String latittude) {
          this.latittude = latittude;
     }

     public String getLocation() {
          return location;
     }

     public void setLocation(String location) {
          this.location = location;
     }

     public String getTelephone() {
          return telephone;
     }

     public void setTelephone(String telephone) {
          this.telephone = telephone;
     }

     public String getAdresse() {
          return adresse;
     }

     public void setAdresse(String adresse) {
          this.adresse = adresse;
     }

     public String getFixe() {
          return fixe;
     }

     public void setFixe(String fixe) {
          this.fixe = fixe;
     }

     public String getBp() {
          return bp;
     }

     public void setBp(String bp) {
          this.bp = bp;
     }

     public String getType() {
          return type;
     }

     public void setType(String type) {
          this.type = type;
     }

     public String getNomCommune() {
          return nomCommune;
     }

     public void setNomCommune(String nomCommune) {
          this.nomCommune = nomCommune;
     }

     public Date getDateCreation() {
          return dateCreation;
     }

     public void setDateCreation(Date dateCreation) {
          this.dateCreation = dateCreation;
     }

     public Status getStatus() {
          return status;
     }

     public void setStatus(Status status) {
          this.status = status;
     }
}
