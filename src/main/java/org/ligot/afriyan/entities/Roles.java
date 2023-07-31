package org.ligot.afriyan.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
public class Roles {
    public Roles() {
    }

    public Roles(Long id, String nom, String description, String fonction) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.fonction = fonction;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDENTIFIANT")
    private Long id;
    @Column(name = "NOM",unique = true,updatable = false)
    private String nom;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "FONCTION")
    private String fonction;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    @Override
    public String toString() {
        return "Roles{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", fonction='" + fonction + '\'' +
                '}';
    }
}
