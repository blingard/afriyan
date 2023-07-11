package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.RapportDto;
import org.ligot.afriyan.entities.Rapport;

import java.util.List;

public interface IRapport {

    Rapport saveRapport(RapportDto rapportDto);
    List<Rapport> listRapport();
    Rapport updateRapport(RapportDto rapportDto, long id);
    void deleteRapport(long id);

}
