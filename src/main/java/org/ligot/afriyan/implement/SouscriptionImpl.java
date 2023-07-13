package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.SouscriptionDTO;
import org.ligot.afriyan.entities.Souscription;
import org.ligot.afriyan.mapper.SouscriptionMapper;
import org.ligot.afriyan.repository.ISouscriptionRepository;
import org.ligot.afriyan.service.ISouscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SouscriptionImpl implements ISouscription {
    private final SouscriptionMapper mapper;

    private ISouscription iSouscription;
    private final ISouscriptionRepository repository;
    private final int PAGE_SIZE = 15;

    public SouscriptionImpl(SouscriptionMapper mapper, ISouscriptionRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public SouscriptionDTO findById(Long id) throws Exception {
        Souscription souscription = repository.findById(id).orElse(null);
        if(souscription == null){
            throw new Exception("Le Souscription que vous souhaitez modifier n'existes pas");
        }
        return mapper.toDTO(souscription);
    }

    @Override
    public SouscriptionDTO save(SouscriptionDTO souscriptionDTO) throws Exception {
        return mapper.toDTO(repository.save(mapper.create(souscriptionDTO)));
    }

    @Override
    public Page<SouscriptionDTO> list(int page) throws Exception {
        Page<Souscription> pages = repository.findAll(PageRequest.of(page,PAGE_SIZE));
        return new PageImpl<>(pages.map(mapper::toDTO).toList(),PageRequest.of(page,PAGE_SIZE),pages.getTotalElements());
    }

    @Override
    public SouscriptionDTO update(SouscriptionDTO souscriptionDTO, Long id) throws Exception {
        Souscription souscription = repository.findById(id).orElse(null);
        if(souscription == null){
            throw new Exception("Le Souscription que vous souhaitez modifier n'existes pas");
        }
        souscriptionDTO.setId(id);
        return mapper.toDTO(repository.save(mapper.create(souscriptionDTO)));
    }

    @Override
    public void delete(Long id) throws Exception {
        repository.deleteById(id);
    }

}
