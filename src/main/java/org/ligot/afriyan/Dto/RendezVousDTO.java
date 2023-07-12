package org.ligot.afriyan.Dto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ligot.afriyan.entities.StatusRdv;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RendezVousDTO {
    private Long id;
    @NotNull
    private String libelle;
    @NotNull
    private Date dateRdv;
    @NotNull
    private String heureDebut;
    @NotNull
    private String heureFin;
    @NotNull
    private UtilisateurDTO utilisateur;
    @NotNull
    private CentrePartenaireDTO centrePartenaire;
    @NotNull
    private StatusRdv rdv;
}
