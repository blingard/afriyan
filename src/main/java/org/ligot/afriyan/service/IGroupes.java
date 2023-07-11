package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.GroupesDto;
import org.ligot.afriyan.entities.Administrateur;
import org.ligot.afriyan.entities.Groupes;

import java.util.List;

public interface IGroupes {

    Groupes saveGroupes(GroupesDto groupesDto);
    List<Administrateur> listGroupes();
    Administrateur updateGroupes(GroupesDto groupesDto, long id);
    void deleteGroupes(long id);

}
