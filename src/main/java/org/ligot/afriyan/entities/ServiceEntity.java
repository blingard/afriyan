package org.ligot.afriyan.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CurrentTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDENTIFIANT")
    private Long id;
    @Column(name = "LIBELLE", unique = true, nullable = false)
    private String libelle;
    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Produit> produits = new HashSet<>();

    @Column (name = "DATE_CREATION")
    @CurrentTimestamp
    private Date dateCreation= new Date();

    @ManyToOne
    @JsonIgnoreProperties("serviceOfferts")
    private CentrePartenaire centrePartenaire;

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

    public Set<Produit> getProduits() {
        return produits;
    }

    public CentrePartenaire getCentrePartenaire() {
        return centrePartenaire;
    }

    public void setCentrePartenaire(CentrePartenaire centrePartenaire) {
        this.centrePartenaire = centrePartenaire;
    }

    public void setProduits(Set<Produit> produits) {
        this.produits = produits;
    }
}
