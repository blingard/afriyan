package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.AdministrationDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IAdministrateur {

    AdministrationDTO save(AdministrationDTO personneDto);
    AdministrationDTO connect(String login, String password);
    boolean codeExist(String code);



}
