package org.ligot.afriyan.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="IDENTIFIANT")
    private Long id;
    @Column(name = "NOM")
    private String nom;
    @Column(name = "LIBELLE")
    private String libelle;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "PRIX")
    private Double prix;
    @Column(name = "DATE_CREATION")
    private Date datCreation;
	@ManyToOne
	private CentrePartenaire centrePartenaire;

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

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public CentrePartenaire getCentrePartenaire() {
        return centrePartenaire;
    }

    public void setCentrePartenaire(CentrePartenaire centrePartenaire) {
        this.centrePartenaire = centrePartenaire;
    }

    public Date getDatCreation() {
        return datCreation;
    }

    public void setDatCreation(Date datCreation) {
        this.datCreation = datCreation;
    }
}
