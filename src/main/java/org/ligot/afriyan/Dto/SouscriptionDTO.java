package org.ligot.afriyan.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ligot.afriyan.entities.Status;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SouscriptionDTO {
    private Long id;
    @NotNull
    private UtilisateurDTO utilisateur;
    @NotNull
    private ServiceDTO service;
    @NotNull
    private Date datecreation;
    @NotNull
    private Status status;
}
