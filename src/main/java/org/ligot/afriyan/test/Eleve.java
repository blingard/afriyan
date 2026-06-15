package org.ligot.afriyan.test;

import jakarta.persistence.*;

@Entity
@Table(name = "eleve")
public class Eleve {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String matricule;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String sexe;
    private String classe;
    private String anneeScolaire;
    private String photo;

    public Eleve() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMatricule() { return matricule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(String dateNaissance) { this.dateNaissance = dateNaissance; }

    public String getSexe() { return sexe; }
    public void setSexe(String sexe) { this.sexe = sexe; }

    public String getClasse() { return classe; }
    public void setClasse(String classe) { this.classe = classe; }

    public String getAnneeScolaire() { return anneeScolaire; }
    public void setAnneeScolaire(String anneeScolaire) { this.anneeScolaire = anneeScolaire; }

    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }
}
