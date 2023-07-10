package org.ligot.afriyan.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nom;
    private String libelle;
    private String description;
    private String prix;
    @ManyToOne
    private CentrePartenaire centrePartenaire;
}
