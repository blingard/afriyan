package org.ligot.afriyan.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CentrePartenaire {

     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     @Column(name = "IDENTIFIANT")
     private Long id;
     @Column(name = "NOM")
     private String nom;
     @Column(name = "LIBELLE")
     private String libelle;
     @Column(name = "DESCRIPTION")
     private String description;
     @Column(name = "longitude", nullable = false)
     private String longitude;
     @Column(name = "LATITTUDE", nullable = false)
     private String latittude;
     @Column(name = "LOCATION", nullable = false)
     private String location;
     @Column(name = "TELEPHONE", nullable = false, unique = true)
     private String telephone;
     @Column(name = "ADRESSE")
     private String adresse;
     @Column(name = "FIXE")
     private String fixe;
     @Column(name = "BOITE_POSTALE")
     private String bp;
     @Column(name = "TYPE_CENTRE")
     private String type;
     @Column(name = "NOM_COMMUNE")
     private String nomCommune;

}
