package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.PersonneDto;
import org.ligot.afriyan.entities.Utilisateur;

import java.util.List;

public interface IUtilisateur {

    Utilisateur saveUtilisateur(PersonneDto personneDto);
    List<Utilisateur> listUtilisateur();
    Utilisateur updateUtilisateur(PersonneDto personneDto, long id);
    void deleteUtilisateur(long id);

}
