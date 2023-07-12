package org.ligot.afriyan.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public abstract class Personne {

    @Id
    @Column(name = "IDENTIFIANT")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="CODE", nullable = false, unique = true)
    private String code;

    @Column(name = "NOM")
    private String nom;

    @Column(name = "PRENOM")
    private String prenom;

    @Column(name = "DATE_NAISSANCE")
    private Date ddn;

    @Column(name = "LIEU_NAISSANCE")
    private String lieu;

    @Column(name = "NUMERO_TELEPHONE", unique = true)
    private String numero_telephone;

    @Column(name = "PHOTO")
    private String photo;

    @Column(name = "LOCATION")
    private String location;

    @Column(name="ANONYMAT")
    private String anonymat;

    @Column(name = "SEXE")
    @Enumerated(EnumType.STRING)
    private Sexe sexe;

    @Column(name = "EMAIL")
    @Email(message = "Veuillez saisir une adresse mail")
    private String email;

    @Column(name="PASSWORD")
    private String pwd;

    @Column(name = "DATECREATION")
    private Date dCreation;


}
