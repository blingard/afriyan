package org.ligot.afriyan.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RendezVous {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDENTIIFNAT")
    private long id;
    @Column(name = "LIBELLE")
    private String libelle;
    @Column(name = "DATE_RDV")
    private Date dateRdv;
    @Column(name = "HEURE_DEBUT")
    private String heureDebut;
    @Column(name = "HEURE_FIN")
    private String heureFin;
    @ManyToOne
    @JoinColumn(name = "UTILISATEUR", referencedColumnName = "IDENTIFIAT")
    private Utilisateur utilisateur;
    @ManyToOne
    @JoinColumn(name = "CENTRE_PARTENAIRE", referencedColumnName = "IDENTIFIANT")
    private CentrePartenaire centrePartenaire;
}
