package org.ligot.afriyan.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ligot.afriyan.entities.Utilisateur;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupesDTO {
    private Long id;

    private Set<RolesDTO> roles;
    @NotNull
    private String name;
    @NotNull
    private String libelle;
    @NotNull
    private String description;
    private Set<UtilisateurDTO> utilisateurs;
}
