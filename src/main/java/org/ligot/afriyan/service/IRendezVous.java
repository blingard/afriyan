package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.RendezVousDto;
import org.ligot.afriyan.entities.RendezVous;

import java.util.List;

public interface IRendezVous {

    RendezVous saveRendezVous(RendezVousDto rendezVousDto);
    List<RendezVous> listRendezVous();
    RendezVous updateRendezVous(RendezVousDto rendezVousDto, long id);
    void deleteRendezVous(long id);

}
