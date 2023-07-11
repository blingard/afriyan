package org.ligot.afriyan.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
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
    private String prix;
    @ManyToOne
    @JoinColumn(name = "CENTRE_PARTENAINE", referencedColumnName = "IDENTIFIANT")
    private CentrePartenaire centrePartenaire;
}
