package org.ligot.afriyan.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CentrePartenaireDto {

    private String nom;
    private String libelle;
    private String description;
    private String longitude;
    private String latittude;
    private String location;
    private String telephone;
    private String adresse;
    private String fixe;
    private String bp;
    private String type;
    private String nomCommune;
}
