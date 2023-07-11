package org.ligot.afriyan.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProduitDto {
    private String nom;
    private String libelle;
    private String description;
    private String prix;
    private CentrePartenaireDto centrePartenaire;
}
