package org.ligot.afriyan.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupesDto {

    private Set<RolesDto> rolesDtos= new HashSet<>();
    private String name;
    private String libelle;
    private String description;
    private Set<PersonneDto> personneDtos = new HashSet<>();
}
