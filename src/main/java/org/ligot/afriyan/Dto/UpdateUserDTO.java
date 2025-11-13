package org.ligot.afriyan.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.ligot.afriyan.echo.dto.CommunesDTO;
import org.ligot.afriyan.echo.dto.DepartementDTO;
import org.ligot.afriyan.echo.dto.LocalitiesDTO;

public class UpdateUserDTO {
    @NotNull
    private UtilisateurDTO utilisateur;
    private CommunesDTO commune;
    private DepartementDTO departement;
    private LocalitiesDTO localities;

    public UpdateUserDTO() {
    }

    public UtilisateurDTO getUtilisateur() {
        return utilisateur;
    }

    public CommunesDTO getCommune() {
        return commune;
    }

    public DepartementDTO getDepartement() {
        return departement;
    }

    public LocalitiesDTO getLocalities() {
        return localities;
    }
}
