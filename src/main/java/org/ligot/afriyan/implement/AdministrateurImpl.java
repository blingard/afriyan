package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.AdministrationDTO;
import org.ligot.afriyan.entities.Administrateur;
import org.ligot.afriyan.mapper.AdministrateurMapper;
import org.ligot.afriyan.repository.IAdministrateurRepository;
import org.ligot.afriyan.service.IAdministrateur;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class AdministrateurImpl implements IAdministrateur {
    private final IAdministrateurRepository repository;
    private final AdministrateurMapper mapper;

    public AdministrateurImpl(IAdministrateurRepository repository, AdministrateurMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public AdministrationDTO save(AdministrationDTO personneDto) {
        return mapper.toDTO(repository.save(mapper.create(personneDto)));
    }

    @Override
    public AdministrationDTO connect(String login, String password) {
        Administrateur administrateur = repository.findByEmailAndPAndPwd(login, password).orElse(null);
        if(administrateur == null){
            return null;
        }
        return mapper.toDTO(administrateur);
    }

    @Override
    public boolean codeExist(String code) {
        Optional<Administrateur> optional = repository.findByCode(code);
        if(optional.isEmpty()){
            return false;
        }
        return true;
    }
}
