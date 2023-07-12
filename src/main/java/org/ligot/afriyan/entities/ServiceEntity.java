package org.ligot.afriyan.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDENTIFIANT")
    private Long id;
    @Column(name = "LIBELLE", unique = true, nullable = false)
    private String libelle;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column (name = "DATE_CREATION")
    private Date dateCreation= new Date();
    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    private Administrateur createur;
}
