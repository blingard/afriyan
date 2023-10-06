package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.SouscriptionDTO;
import org.ligot.afriyan.entities.*;
import org.ligot.afriyan.mapper.SouscriptionMapper;
import org.ligot.afriyan.repository.IServiceEntityRepository;
import org.ligot.afriyan.repository.ISouscriptionRepository;
import org.ligot.afriyan.service.ISouscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SouscriptionImpl implements ISouscription {
    private final SouscriptionMapper mapper;
    private final ISouscriptionRepository repository;
    private final IServiceEntityRepository iServiceEntityRepository;
    private final int PAGE_SIZE = 15;

    public SouscriptionImpl(SouscriptionMapper mapper, ISouscriptionRepository repository, IServiceEntityRepository iServiceEntityRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.iServiceEntityRepository = iServiceEntityRepository;
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
    public List<SouscriptionDTO> findByIdUser(Long id) throws Exception {
        return repository.findAllByUtilisateur(new Utilisateur(id)).stream().map(mapper::toDTO).toList();
    }

    @Override
    public List<SouscriptionDTO> findByIdService(Long id) throws Exception {
        return repository.findAllByServiceOrderByDatecreationDesc(new ServiceEntity(id)).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<SouscriptionDTO> findByIdCP(Long id) throws Exception {
        List<ServiceEntity> serviceEntityList = iServiceEntityRepository.findServiceEntitiesByCentrePartenaire(new CentrePartenaire(id));
        List<SouscriptionDTO> souscriptionDTOList = new ArrayList<>(0);
        serviceEntityList.stream().forEach(
                service -> {
                    souscriptionDTOList.addAll(
                            repository.findAllByServiceOrderByDatecreationDesc(service).stream().map(mapper::toDTO).collect(Collectors.toList())
                    );
                }
        );
        return null;
    }

    @Override
    public SouscriptionDTO save(SouscriptionDTO souscriptionDTO) throws Exception {
        Optional<Souscription> souscription = repository.findAllByUtilisateurAndStatus(new Utilisateur(souscriptionDTO.getUtilisateur().getId()), Status.ACTIVE);
        if(!souscription.isEmpty())
            throw new Exception("vous avec deja une souscription encours");
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
        return mapper.toDTO(repository.save(mapper.create(souscriptionDTO)));
    }

    @Override
    public void delete(Long id) throws Exception {
        repository.deleteById(id);
    }

}
