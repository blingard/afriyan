package org.ligot.afriyan.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Groupes {
    public Groupes() {
    }

    public Groupes(Long id) {
        this.id = id;
    }

    public Groupes(Long id, Set<Roles> roles, String name, String libelle, String description, Set<Utilisateur> utilisateurs) {
        this.id = id;
        this.roles = roles;
        this.name = name;
        this.libelle = libelle;
        this.description = description;
        this.utilisateurs = utilisateurs;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDENTIFIANT")
    private Long id;

    @OneToMany
    @JoinColumn(name = "ROLES", referencedColumnName = "IDENTIFIANT")
    private Set<Roles> roles= new HashSet<>();

    @Column(name = "NOM", unique = true)
    private String name;

    @Column(name = "LIBELLE")
    private String libelle;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany
    @JoinColumn(name = "UTILISATEURS", referencedColumnName = "IDENTIFIANT")
    private Set<Utilisateur> utilisateurs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Set<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }
}
