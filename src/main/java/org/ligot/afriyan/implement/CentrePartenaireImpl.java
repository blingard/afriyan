package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Constantes;
import org.ligot.afriyan.Dto.CentrePartenaireDTO;
import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.elearning.entities.Paragraphs;
import org.ligot.afriyan.entities.CentrePartenaire;
import org.ligot.afriyan.entities.Status;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CentrePartenaireImpl implements ICentrePartenaire {
    private final CentrePartenaireMapper mapper;
    private final ICentrePartenaireRepository repository;
    private final FileStorageService fileStorageService;
    private UtilisateurMapper utilisateurMapper;
    private final IUtilisateur utilisateur;
    private final int PAGE_SIZE = 15;

    public CentrePartenaireImpl(CentrePartenaireMapper mapper, ICentrePartenaireRepository repository, FileStorageService fileStorageService, UtilisateurMapper utilisateurMapper, IUtilisateur utilisateur) {
        this.mapper = mapper;
        this.repository = repository;
        this.fileStorageService = fileStorageService;
        this.utilisateurMapper = utilisateurMapper;
        this.utilisateur = utilisateur;
    }

    @Override
    public CentrePartenaireDTO findById(Long id) throws Exception {
        CentrePartenaire centrePartenaire = repository.findById(id).orElse(null);
        if(centrePartenaire == null){
            throw new Exception("Le CentrePartenaire que vous souhaitez modifier n'existes pas");
        }
        return findWithFile(centrePartenaire);
    }

    private CentrePartenaireDTO findWithFile(CentrePartenaire centrePartenaire){
        String[] elements = centrePartenaire.getPhoto().split(":");
        Arrays.stream(elements).forEach(System.err::println);
        String imageBase64 = fileStorageService.convertImageToBase64(Constantes.CENTREPARTENAIREIMAGESUBPATH1+elements[0]);
        String image = "data:image/"+elements[1]+";base64,"+imageBase64;
        CentrePartenaireDTO centrePartenaireDTO = mapper.toDTO(centrePartenaire);
        centrePartenaireDTO.setPhoto(image);
        return centrePartenaireDTO;
    }

    @Override
    public CentrePartenaireDTO save(MultipartFile file, CentrePartenaireDTO centrePartenaireDTO) throws Exception {
        Utilisateur utilisateur = getUser();
        centrePartenaireDTO.setCreateur(new UtilisateurDTO(utilisateur.getId()));
        String name = fileStorageService.storeParagraphFileImage(file, Constantes.CENTREPARTENAIREIMAGESUBPATH);
        CentrePartenaire centrePartenaire = mapper.create(centrePartenaireDTO);
        centrePartenaire.setPhoto(name);
        return mapper.toDTO(repository.save(centrePartenaire));
    }
    private Utilisateur getUser() throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UtilisateurDTO utilisateurDTO = utilisateur.findByName(username);
        return utilisateurMapper.create(utilisateurDTO);
    }

    @Override
    public Page<CentrePartenaireDTO> list(int page) throws Exception {
        Page<CentrePartenaire> pages = repository.findAll(PageRequest.of(page,PAGE_SIZE));
        return new PageImpl<>(pages.map(this::findWithFile).toList(),PageRequest.of(page,PAGE_SIZE),pages.getTotalElements());
    }

    @Override
    public List<CentrePartenaireDTO> list() throws Exception {
        return repository.findCentrePartenaireByStatus(Status.ACTIVE).stream().map(this::findWithFile).toList();
    }

    @Override
    public List<CentrePartenaireDTO> listAll(){
        return repository.findAll().stream().map(this::findWithFile).toList();
    }

    @Override
    public CentrePartenaireDTO update(CentrePartenaireDTO centrePartenaireDTO, Long id) throws Exception {
        CentrePartenaire centrePartenaire = repository.findById(id).orElse(null);
        if(centrePartenaire == null){
            throw new Exception("Le CentrePartenaire que vous souhaitez modifier n'existes pas");
        }
        centrePartenaireDTO.setId(id);
        mapper.update(centrePartenaireDTO, centrePartenaire);
        return mapper.toDTO(repository.save(mapper.create(centrePartenaireDTO)));
    }

    @Override
    public void delete(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public CentrePartenaireDTO findByUserId(Long id) throws Exception {
        UtilisateurDTO userDTO = utilisateur.findById(id);
        if(userDTO == null)
            throw new Exception("user with ID = "+id+" is null");
        return mapper.toDTO(repository.findCentrePartenaireByCreateur(new Utilisateur(id)));
    }

    @Override
    public void active(Long id) {
        CentrePartenaire centrePartenaire = repository.findById(id).orElse(null);
        if(centrePartenaire != null){
            centrePartenaire.setStatus(centrePartenaire.getStatus() == Status.ACTIVE ? Status.INACTIVE : Status.ACTIVE);
            repository.save(centrePartenaire);
        }
    }
}
