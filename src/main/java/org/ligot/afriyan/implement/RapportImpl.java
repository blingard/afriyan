package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.RapportDTO;
import org.ligot.afriyan.entities.Rapport;
import org.ligot.afriyan.mapper.RapportMapper;
import org.ligot.afriyan.repository.IRapportRepository;
import org.ligot.afriyan.service.IRapport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RapportImpl implements IRapport {

    private final int PAGE_SIZE = 15;
    private final RapportMapper mapper;
    private final IRapportRepository repository;

    public RapportImpl(RapportMapper mapper, IRapportRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public RapportDTO findById(Long id) throws Exception {
        Rapport rapport = repository.findById(id).orElse(null);
        if(rapport == null){
            throw new Exception("Le Rapport que vous souhaitez modifier n'existes pas");
        }
        return mapper.toDTO(rapport);
    }

    @Override
    public RapportDTO save(RapportDTO rapportDto) throws Exception{
        return mapper.toDTO(repository.save(mapper.create(rapportDto)));
    }

    @Override
    public Page<RapportDTO> list(int page) {
        Page<Rapport> pages = repository.findAll(PageRequest.of(page,PAGE_SIZE));
        return new PageImpl<>(pages.map(mapper::toDTO).toList(),PageRequest.of(page,PAGE_SIZE),pages.getTotalElements());
    }

    @Override
    public RapportDTO update(RapportDTO rapportDto, Long id) throws Exception {
        Rapport rapport = repository.findById(id).orElse(null);
        if (rapport == null){
            throw new Exception("Le Rapportq que vous souhaitez modifier n'existes pas");
        }

        return mapper.toDTO(repository.saveAndFlush(mapper.create(rapportDto)));
    }

    @Override
    public void delete(Long id) throws Exception{
        repository.deleteById(id);
    }
}
