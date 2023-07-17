package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.AdministrationDTO;
import org.ligot.afriyan.entities.Administrateur;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IAdministrateur {

    AdministrationDTO save(AdministrationDTO personneDto);
    Administrateur save(Administrateur personne);
    AdministrationDTO connect(String login, String password);
    boolean codeExist(String code);



}
