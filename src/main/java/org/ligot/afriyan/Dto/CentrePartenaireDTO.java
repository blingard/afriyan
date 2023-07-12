package org.ligot.afriyan.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ligot.afriyan.entities.Status;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CentrePartenaireDTO {
    private Long id;
    @NotNull
    private String nom;
    @NotNull
    private String libelle;
    @NotNull
    private String description;
    @NotNull
    private String longitude;
    @NotNull
    private String latittude;
    @NotNull
    private String location;
    @NotNull
    private String telephone;
    @NotNull
    private String adresse;
    @NotNull
    private String fixe;
    @NotNull
    private String bp;
    @NotNull
    private String type;
    @NotNull
    private String nomCommune;
    @NotNull
    private Status status;
}
