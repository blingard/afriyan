package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.CentrePartenaireDTO;
import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.entities.CentrePartenaire;
import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.mapper.CentrePartenaireMapper;
import org.ligot.afriyan.mapper.UtilisateurMapper;
import org.ligot.afriyan.repository.ICentrePartenaireRepository;
import org.ligot.afriyan.service.ICentrePartenaire;
import org.ligot.afriyan.service.IUtilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CentrePartenaireImpl implements ICentrePartenaire {
    private final CentrePartenaireMapper mapper;
    private final ICentrePartenaireRepository repository;

    private UtilisateurMapper utilisateurMapper;

    private final IUtilisateur utilisateur;
    private final int PAGE_SIZE = 15;

    public CentrePartenaireImpl(CentrePartenaireMapper mapper, ICentrePartenaireRepository repository, IUtilisateur utilisateur) {
        this.mapper = mapper;
        this.repository = repository;
        this.utilisateur = utilisateur;
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
    public CentrePartenaireDTO save(CentrePartenaireDTO centrePartenaireDTO) throws Exception {
        Utilisateur utilisateur = getUser();
        return mapper.toDTO(repository.save(mapper.create(centrePartenaireDTO)));
    }
    private Utilisateur getUser() throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UtilisateurDTO utilisateurDTO = utilisateur.findByName(username);
        return utilisateurMapper.create(utilisateurDTO);
    }

    @Override
    public Page<CentrePartenaireDTO> list(int page) throws Exception {
        Page<CentrePartenaire> pages = repository.findAll(PageRequest.of(page,PAGE_SIZE));
        return new PageImpl<>(pages.map(mapper::toDTO).toList(),PageRequest.of(page,PAGE_SIZE),pages.getTotalElements());
    }

    @Override
    public List<CentrePartenaireDTO> list() throws Exception {
        return repository.findAll().stream().map(mapper::toDTO).toList();
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
