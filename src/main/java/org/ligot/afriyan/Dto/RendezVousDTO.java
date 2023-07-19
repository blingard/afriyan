package org.ligot.afriyan.Dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.ligot.afriyan.entities.StatusRdv;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
public class RendezVousDTO {
    private Long id;
    private String libelle;
    private Date dateRdv;
    private String heureDebut;
    private String heureFin;
    @JsonIgnoreProperties({"groupe"})
    private UtilisateurDTO utilisateur;
    @JsonIgnoreProperties({"serviceOfferts","produits","createur"})
    private CentrePartenaireDTO centrePartenaire;
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

    public UtilisateurDTO getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(UtilisateurDTO utilisateur) {
        this.utilisateur = utilisateur;
    }

    public CentrePartenaireDTO getCentrePartenaire() {
        return centrePartenaire;
    }

    public void setCentrePartenaire(CentrePartenaireDTO centrePartenaire) {
        this.centrePartenaire = centrePartenaire;
    }

    public StatusRdv getRdv() {
        return rdv;
    }

    public void setRdv(StatusRdv rdv) {
        this.rdv = rdv;
    }
}
