package org.ligot.afriyan.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublicationsDto {

    private String type;
    private String contenu;
    private String categorie;
    private PersonneDto personneDto;
    private ServiceDto service;
}
