package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.MessageDto;
import org.ligot.afriyan.Dto.RolesDto;
import org.ligot.afriyan.entities.Message;
import org.ligot.afriyan.entities.Roles;

import java.util.List;

public interface IRoles {

    Roles saveRoles(RolesDto rolesDto);
    List<Roles> listRoles();
    Message updateRoles(RolesDto rolesDto, long id);
    void deleteRoles(long id);

}
