package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.DenonciationDTO;
import org.ligot.afriyan.entities.Denonciation;
import org.ligot.afriyan.entities.Message;
import org.ligot.afriyan.entities.Produit;
import org.ligot.afriyan.entities.TypeDonne;
import org.ligot.afriyan.mapper.DenonciationMapper;
import org.ligot.afriyan.repository.IDenonciationRepository;
import org.ligot.afriyan.service.IDenonciation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DenonciationImpl implements IDenonciation {
    private final IDenonciationRepository repository;
    private final DenonciationMapper mapper;

    private final int PAGE_SIZE = 15;


    public DenonciationImpl(IDenonciationRepository repository, DenonciationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public DenonciationDTO save(DenonciationDTO denonciationDTO) throws Exception{
        return mapper.toDTO(repository.save(mapper.create(denonciationDTO)));
    }

    @Override
    public DenonciationDTO findById(Long id) throws Exception {
        Denonciation denonciation = repository.findById(id).orElse(null);
        if(denonciation == null)
            throw new Exception("data not found");
        return mapper.toDTO(denonciation);
    }

    @Override
    public Page<DenonciationDTO> getPage(int page) throws Exception{
        Page<Denonciation> pages = repository.findAll(PageRequest.of(page,PAGE_SIZE));
        return new PageImpl<>(pages.map(mapper::toDTO).toList(),PageRequest.of(page,PAGE_SIZE),pages.getTotalElements());
    }

    @Override
    public DenonciationDTO update(DenonciationDTO denonciationDTO, Long id) throws Exception{

        Denonciation denonciation = repository.findById(id).orElse(null);
        if(denonciation == null){
            throw new Exception("Le Denonciation que vous souhaitez modifier n'existes pas");
        }
        denonciationDTO.setId(id);

        return mapper.toDTO(repository.saveAndFlush(mapper.create(denonciationDTO)));
    }

    @Override
    public void delete(Long id) throws Exception{
        Denonciation denonciation = repository.findById(id).orElse(null);
        if(denonciation != null)
            repository.delete(denonciation);
    }
}
