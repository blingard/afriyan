package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.RendezVousDTO;
import org.ligot.afriyan.entities.RendezVous;
import org.ligot.afriyan.mapper.RendezVousMapper;
import org.ligot.afriyan.repository.IRendezVousRepository;
import org.ligot.afriyan.service.IRendezVous;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RendezVousImpl implements IRendezVous {

    private final int PAGE_SIZE = 15;
    private final RendezVousMapper mapper;
    private final IRendezVousRepository repository;

    public RendezVousImpl(RendezVousMapper mapper, IRendezVousRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public RendezVousDTO findById(Long id) throws Exception {
        RendezVous rendezVous = repository.findById(id).orElse(null);
        if(rendezVous == null){
            throw new Exception("Le RendezVous que vous souhaitez modifier n'existes pas");
        }
        return mapper.toDTO(rendezVous);
    }

    @Override
    public List<RendezVousDTO> findByUserId(Long id) throws Exception {
        return repository.findRendezVousByUtilisateur_Id(id).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public RendezVousDTO save(RendezVousDTO rendezVousDto) throws Exception{
        return mapper.toDTO(repository.save(mapper.create(rendezVousDto)));
    }

    @Override
    public Page<RendezVousDTO> list(int page) {
        Page<RendezVous> pages = repository.findAll(PageRequest.of(page,PAGE_SIZE));
        return new PageImpl<>(pages.map(mapper::toDTO).toList(),PageRequest.of(page,PAGE_SIZE),pages.getTotalElements());
    }

    @Override
    public Page<RendezVousDTO> list(int page, Long idUser) throws Exception{
        List<RendezVous> rendezVous = repository.findRendezVousByUtilisateur_Id(idUser);
        //return new PageImpl<>(rendezVous.map(mapper::toDTO).toList(),PageRequest.of(page,PAGE_SIZE),rendezVous.getTotalElements());
        return null;
    }

    @Override
    public RendezVousDTO update(RendezVousDTO rendezVousDto, Long id) throws Exception {
        RendezVous rendezVous = repository.findById(id).orElse(null);
        if (rendezVous == null){
            throw new Exception("Le RendezVousq que vous souhaitez modifier n'existes pas");
        }

        return mapper.toDTO(repository.saveAndFlush(mapper.create(rendezVousDto)));
    }

    @Override
    public void delete(Long id) throws Exception{
        repository.deleteById(id);
    }
}
