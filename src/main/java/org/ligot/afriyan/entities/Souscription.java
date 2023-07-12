package org.ligot.afriyan.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Souscription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDENTIFIANT")
    private Long id;

    @ManyToOne
    private Utilisateur utilisateur;

    @ManyToOne
    private ServiceEntity service;

    @Column(name = "datecreation")
    private Date datecreation;

    @Enumerated(EnumType.STRING)
    private Status status;
}
