package org.ligot.afriyan.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RendezVous {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDENTIFIANT")
    private Long id;
    @Column(name = "LIBELLE")
    private String libelle;
    @Column(name = "DATE_RDV")
    private Date dateRdv;
    @Column(name = "HEURE_DEBUT")
    private String heureDebut;
    @Column(name = "HEURE_FIN")
    private String heureFin;
    @ManyToOne
    @JoinColumn(name = "UTILISATEUR", referencedColumnName = "IDENTIFIANT")
    private Utilisateur utilisateur;
    @ManyToOne
    @JoinColumn(name = "CENTRE_PARTENAIRE", referencedColumnName = "IDENTIFIANT")
    private CentrePartenaire centrePartenaire;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private StatusRdv rdv;

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

    public Date getDateRdv() {
        return dateRdv;
    }

    public void setDateRdv(Date dateRdv) {
        this.dateRdv = dateRdv;
    }

    public String getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public String getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public CentrePartenaire getCentrePartenaire() {
        return centrePartenaire;
    }

    public void setCentrePartenaire(CentrePartenaire centrePartenaire) {
        this.centrePartenaire = centrePartenaire;
    }

    public StatusRdv getRdv() {
        return rdv;
    }

    public void setRdv(StatusRdv rdv) {
        this.rdv = rdv;
    }
}
