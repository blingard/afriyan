package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.CentrePartenaireDto;
import org.ligot.afriyan.entities.CentrePartenaire;

import java.util.List;

public interface ICentrePartenaire {

    CentrePartenaire saveUtilisateur(CentrePartenaireDto centrePartenaireDto);
    List<CentrePartenaire> listCentrePartenaire();
    CentrePartenaire updateCentrePartenaire(CentrePartenaireDto centrePartenaireDto, long id);
    void deleteCentrePartenaire(long id);

}
