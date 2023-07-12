package org.ligot.afriyan.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProduitDTO {

    private Long id;
    @NotNull
    private String nom;
    @NotNull
    private String libelle;
    @NotNull
    private String description;
    @NotNull
    private Double prix;
    @NotNull
    private CentrePartenaireDTO centrePartenaireDTO;
}
