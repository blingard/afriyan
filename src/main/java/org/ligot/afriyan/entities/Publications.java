package org.ligot.afriyan.entities;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Publications {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDENTIFIANT")
    private Long id;
    @Column(name = "TYPE", nullable = false)
    private String type;
    @Column(name = "CONTENU")
    private String contenu;
    @Column(name = "CATEGORIE")
    private String categorie;

    @ManyToOne
    @JoinColumn(name = "ADMINISTRATEUR", referencedColumnName = "IDENTIFIANT")
    private Utilisateur administrateur;

    @ManyToOne
    @JoinColumn(name = "SERVICE", referencedColumnName = "IDENTIFIANT")
    private ServiceEntity service;
    @Column(name = "LIEN_TWITER")
    private String reseau1;
    @Column(name = "LIEN_FACEBOOK")
    private String reseau2;
    @Column(name = "AUTRES")
    private String reseau3;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Utilisateur getAdministrateur() {
        return administrateur;
    }

    public void setAdministrateur(Utilisateur administrateur) {
        this.administrateur = administrateur;
    }

    public ServiceEntity getService() {
        return service;
    }

    public void setService(ServiceEntity service) {
        this.service = service;
    }

    public String getReseau1() {
        return reseau1;
    }

    public void setReseau1(String reseau1) {
        this.reseau1 = reseau1;
    }

    public String getReseau2() {
        return reseau2;
    }

    public void setReseau2(String reseau2) {
        this.reseau2 = reseau2;
    }

    public String getReseau3() {
        return reseau3;
    }

    public void setReseau3(String reseau3) {
        this.reseau3 = reseau3;
    }
}
