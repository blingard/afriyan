package org.ligot.afriyan.Dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ligot.afriyan.entities.Status;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private Long id;
    @NotNull
    private String objects;
    @NotNull
    private String corps;
    @NotNull
    private Status status;
    private UtilisateurDTO utilisateur;
}
