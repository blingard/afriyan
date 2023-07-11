package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.PersonneDto;
import org.ligot.afriyan.entities.Administrateur;

import java.util.List;

public interface IAdministrateur {

    Administrateur saveAdministrateur(PersonneDto personneDto);
    List<Administrateur> listAdministrateur();
    Administrateur updateAdministrateur(PersonneDto personneDto, long id);
    void deleteAdministrateur(long id);

}
