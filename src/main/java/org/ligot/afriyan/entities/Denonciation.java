package org.ligot.afriyan.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Denonciation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(length = -1)
    private String contenu;

    private Date dateDenonciation = new Date();

    @ManyToOne
    private Utilisateur utilisateur;

    public void setId(Long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDateDenonciation() {
        return dateDenonciation;
    }

    public void setDatePublication(Date dateDenonciation) {
        this.dateDenonciation = dateDenonciation;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
