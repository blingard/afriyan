package org.ligot.afriyan.entities;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
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


}
