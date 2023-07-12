package org.ligot.afriyan.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdministrationDTO extends PersonneDTO{
    private Set<RolesDTO> roles;
}
