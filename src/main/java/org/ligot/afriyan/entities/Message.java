package org.ligot.afriyan.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDENTIFIANT")
    private long id;
    @Column(name = "OBJECT")
    private String object;
    @Column(name = "CORPS")
    private String corps;
    @OneToMany
    private Collection<Utilisateur> utilisateurs;
    @OneToMany
    private Collection<Administrateur> administrateurs;
}
