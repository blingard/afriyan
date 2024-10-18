package org.ligot.afriyan.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
public class CentrePartenaire {

     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     @Column(name = "IDENTIFIANT")
     private Long id;
     @Column(name = "NOM", unique = true)
     private String nom;
     @Column(name = "LIBELLE",unique = true)
     private String libelle;
     @Column(name = "DESCRIPTION",length = 10000)
     private String description;
     @Column(name = "longitude", nullable = false)
     private String longitude;
     @Column(name = "LATITTUDE", nullable = false)
     private String latittude;
     @Column(name = "LOCATION", nullable = false)
     private String location;
     @Column(name = "TELEPHONE", nullable = false, unique = true)
     private String telephone;
     @Column(name = "PHOTO")
     private String photo;
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
     @CurrentTimestamp
     private Date dateCreation;
     @Enumerated(EnumType.STRING)
     private Status status;

     @OneToOne(fetch = FetchType.EAGER)
     @JsonIgnoreProperties("groupe")
     private Utilisateur createur;

     @OneToMany(fetch = FetchType.EAGER)
     private Set<ServiceEntity> serviceOfferts = new HashSet<>();

     @Column(columnDefinition = "numeric(10, 2) default 0.0")
     private Double superficie;

     @Column(columnDefinition = "integer default 0")
     private int nombreLit;

     @Column(columnDefinition = "integer default 0")
     private int nombreBat;

     @Column(columnDefinition = "integer default 0")
     private int category;

     @Column(columnDefinition = "boolean default false")
     private boolean morgue;

     @Column(columnDefinition = "varchar(255) default 'region'")
     private String region;



     public CentrePartenaire(Long id) {
          this.id = id;
     }

     public CentrePartenaire() {
     }

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

     public Utilisateur getCreateur() {
          return createur;
     }

     public void setCreateur(Utilisateur createur) {
          this.createur = createur;
     }

     public Set<ServiceEntity> getServiceOfferts() {
          return serviceOfferts;
     }

     public void setServiceOfferts(Set<ServiceEntity> serviceOfferts) {
          this.serviceOfferts = serviceOfferts;
     }


     public String getPhoto() {
          return photo;
     }

     public void setPhoto(String photo) {
          this.photo = photo;
     }

     public Double getSuperficie() {
          return superficie;
     }

     public void setSuperficie(Double superficie) {
          this.superficie = superficie;
     }

     public int getNombreLit() {
          return nombreLit;
     }

     public void setNombreLit(int nombreLit) {
          this.nombreLit = nombreLit;
     }

     public int getNombreBat() {
          return nombreBat;
     }

     public void setNombreBat(int nombreBat) {
          this.nombreBat = nombreBat;
     }

     public int getCategory() {
          return category;
     }

     public void setCategory(int category) {
          this.category = category;
     }

     public boolean isMorgue() {
          return morgue;
     }

     public void setMorgue(boolean morgue) {
          this.morgue = morgue;
     }

     public String getRegion() {
          return region;
     }

     public void setRegion(String region) {
          this.region = region;
     }
}
