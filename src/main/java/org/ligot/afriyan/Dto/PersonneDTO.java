package org.ligot.afriyan.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class PersonneDTO {

    private Long id;
    @NotNull
    private String code;
    @NotNull
    private String nom;
    @NotNull
    private String prenom;
    @NotNull
    private String ddn;
    @NotNull
    private String lieu;
    @NotNull
    private String numero_telephone;
    @NotNull
    private String photo;
    @NotNull
    private String location;
    @NotNull
    private String anonymat;
    @NotNull
    private String sexe;
    @NotNull
    @Email
    private String email;

}
