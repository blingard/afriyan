package org.ligot.afriyan.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
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
    private Administrateur administrateur;
    @ManyToOne
    @JoinColumn(name = "SERVICE", referencedColumnName = "IDENTIFIANT")
    private Service service;

}
