package org.ligot.afriyan.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ligot.afriyan.entities.Publications;
import org.ligot.afriyan.entities.Utilisateur;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublicationsDTO {

    private Long id;
    private String type;
    private String contenu;
    private String categorie;
    private UtilisateurDTO administrateur;
    private ServiceDTO service;
}
