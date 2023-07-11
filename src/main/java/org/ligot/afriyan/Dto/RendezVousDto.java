package org.ligot.afriyan.Dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RendezVousDto {

    private String libelle;
    private Date dateRdv;
    private String heureDebut;
    private String heureFin;
    private PersonneDto personneDto;
    private CentrePartenaireDto centrePartenaireDto;
}
