package org.ligot.afriyan.Dto;
import org.ligot.afriyan.entities.StatusRdv;

import java.util.Date;


public record RendezVousRequest (
        String libelle,
        Date dateRdv,
        String heureDebut,
        String heureFin,
        Long productId,
        StatusRdv rdv) { }
