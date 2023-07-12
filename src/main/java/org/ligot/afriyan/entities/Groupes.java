package org.ligot.afriyan.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Groupes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDENTIFIANT")
    private Long id;

    @OneToMany
    @JoinColumn(name = "ROLES", referencedColumnName = "IDENTIFIANT")
    private Set<Roles> roles= new HashSet<>();

    @Column(name = "NOM")
    private String name;

    @Column(name = "LIBELLE")
    private String libelle;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany
    @JoinColumn(name = "UTILISATEURS", referencedColumnName = "IDENTIFIANT")
    private Set<Utilisateur> utilisateurs = new HashSet<>();


}
