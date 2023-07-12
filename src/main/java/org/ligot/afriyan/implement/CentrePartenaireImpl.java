package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.CentrePartenaireDTO;
import org.ligot.afriyan.entities.CentrePartenaire;
import org.ligot.afriyan.mapper.CentrePartenaireMapper;
import org.ligot.afriyan.repository.ICentrePartenaireRepository;
import org.ligot.afriyan.service.ICentrePartenaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CentrePartenaireImpl implements ICentrePartenaire {
    private final CentrePartenaireMapper mapper;
    private final ICentrePartenaireRepository repository;
    private final int PAGE_SIZE = 15;

    public CentrePartenaireImpl(CentrePartenaireMapper mapper, ICentrePartenaireRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public CentrePartenaireDTO findById(Long id) throws Exception {
        CentrePartenaire centrePartenaire = repository.findById(id).orElse(null);
        if(centrePartenaire == null){
            throw new Exception("Le CentrePartenaire que vous souhaitez modifier n'existes pas");
        }
        return mapper.toDTO(centrePartenaire);
    }

    @Override
    public CentrePartenaireDTO save(CentrePartenaireDTO centrePartenaireDTO) {
        return mapper.toDTO(repository.save(mapper.create(centrePartenaireDTO)));
    }

    @Override
    public Page<CentrePartenaireDTO> list(int page) throws Exception {
        Page<CentrePartenaire> pages = repository.findAll(PageRequest.of(page,PAGE_SIZE));
        return new PageImpl<>(pages.map(mapper::toDTO).toList(),PageRequest.of(page,PAGE_SIZE),pages.getTotalElements());
    }

    @Override
    public CentrePartenaireDTO update(CentrePartenaireDTO centrePartenaireDTO, Long id) throws Exception {
        CentrePartenaire centrePartenaire = repository.findById(id).orElse(null);
        if(centrePartenaire == null){
            throw new Exception("Le CentrePartenaire que vous souhaitez modifier n'existes pas");
        }
        centrePartenaireDTO.setId(id);
        return mapper.toDTO(repository.save(mapper.create(centrePartenaireDTO)));
    }

    @Override
    public void delete(Long id) throws Exception {
        repository.deleteById(id);
    }
}
