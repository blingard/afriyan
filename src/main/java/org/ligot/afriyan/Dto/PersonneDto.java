package org.ligot.afriyan.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonneDto {

    private String code;
    private String nom;
    private String prenom;
    private String ddn;
    private String lieu;
    private String numero_telephone;
    private String photo;
    private String location;
    private String anonymat;
    private String sexe;
    private String email;

}
