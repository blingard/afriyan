package org.ligot.afriyan.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Personne {

    @Id
    @Column(name = "IDENTIFIANT")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name="CODE", nullable = false, unique = true)
    private String code;
    @Column(name = "NOM")
    private String nom;
    @Column(name = "PRENOM")
    private String prenom;
    @Column(name = "DATE_NAISSANCE")
    private String ddn;
    @Column(name = "LIEU_NAISSANCE")
    private String lieu;
    @Column(name = "NUMERO_TELEPHONE")
    private String numero_telephone;
    @Column(name = "PHOTO")
    private String photo;
    @Column(name = "LOCATION")
    private String location;
    @Column(name="ANONYMAT")
    private String anonymat;

}
