package org.ligot.afriyan.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
public class ServiceDTO {
    private Long id;
    private String libelle;
    private String description;
    @JsonIgnore
    private Set<ProduitDTO> produits = new HashSet<>();
    private Date dateCreation;
    @JsonIgnoreProperties({"serviceOfferts"})
    private CentrePartenaireDTO centrePartenaire;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Set<ProduitDTO> getProduits() {
        return produits;
    }

    public CentrePartenaireDTO getCentrePartenaire() {
        return centrePartenaire;
    }

    public void setCentrePartenaire(CentrePartenaireDTO centrePartenaire) {
        this.centrePartenaire = centrePartenaire;
    }

    public void setProduits(Set<ProduitDTO> produits) {
        this.produits = produits;
    }
}
