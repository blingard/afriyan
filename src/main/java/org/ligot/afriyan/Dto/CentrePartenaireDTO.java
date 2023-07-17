package org.ligot.afriyan.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.ligot.afriyan.entities.Status;
import org.ligot.afriyan.entities.Utilisateur;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
public class CentrePartenaireDTO {
    private Long id;
    @NotNull
    private String nom;
    @NotNull
    private String libelle;
    @NotNull
    private String description;
    @NotNull
    private String longitude;
    @NotNull
    private String latittude;
    @NotNull
    private String location;
    @NotNull
    private String telephone;
    @NotNull
    private String adresse;
    @NotNull
    private String fixe;
    @NotNull
    private String bp;
    @NotNull
    private String type;
    @NotNull
    private String nomCommune;
    @NotNull
    private String photo;

    private Set<ServiceDTO> serviceOfferts = new HashSet<>();
    private Set<ProduitDTO> produits = new HashSet<>();

    private UtilisateurDTO createur;
    @NotNull
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Set<ServiceDTO> getServiceOfferts() {
        return serviceOfferts;
    }

    public void setServiceOfferts(Set<ServiceDTO> serviceOfferts) {
        this.serviceOfferts = serviceOfferts;
    }

    public Set<ProduitDTO> getProduits() {
        return produits;
    }

    public void setProduits(Set<ProduitDTO> produits) {
        this.produits = produits;
    }

    public UtilisateurDTO getCreateur() {
        return createur;
    }

    public void setCreateur(UtilisateurDTO createur) {
        this.createur = createur;
    }
}
