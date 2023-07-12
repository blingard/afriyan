package org.ligot.afriyan.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
public class AdministrationDTO extends PersonneDTO{
    private Set<RolesDTO> roles;

    public Set<RolesDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RolesDTO> roles) {
        this.roles = roles;
    }
}
