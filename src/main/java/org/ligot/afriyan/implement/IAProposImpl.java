package org.ligot.afriyan.implement;

import org.ligot.afriyan.Dto.AProposDTO;
import org.ligot.afriyan.entities.APropos;
import org.ligot.afriyan.mapper.AProposMapper;
import org.ligot.afriyan.repository.IAProposRepository;
import org.ligot.afriyan.service.IAPropos;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IAProposImpl implements IAPropos {
    private final IAProposRepository repository;
    private final AProposMapper mapper;

    public IAProposImpl(IAProposRepository repository, AProposMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(AProposDTO aProposDTO) throws Exception {
        if(repository.count()>0L)
            throw new Exception("La section APropos existe deja");
        repository.save(mapper.create(aProposDTO));
    }

    @Override
    public void update(Long id, AProposDTO aProposDTO) throws Exception {
        if(!id.equals(aProposDTO.getId()))
            throw new Exception("Information non concordante");
        APropos aPropos = repository.findById(id).orElse(null);
        if(aPropos==null)
            throw new Exception("Donnee non trouver");
        mapper.update(aProposDTO,aPropos);
        repository.save(aPropos);
    }

    @Override
    public AProposDTO get() {
        List<APropos> aPropos = repository.findAll();
        if(aPropos.isEmpty())
            return null;
        return mapper.toDTO(repository.findAll().get(0));
    }
}
