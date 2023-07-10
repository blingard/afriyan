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
    private long id;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "CONTENU")
    private String contenu;
    @Column(name = "CATEGORIE")
    private String categorie;
    @ManyToOne
    @JoinColumn(name = "ADMINISTRATEUR", referencedColumnName = "IDENTIFIANT")
    private Administrateur administrateur;

}
