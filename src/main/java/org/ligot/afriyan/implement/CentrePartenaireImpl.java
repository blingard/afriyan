package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Constantes;
import org.ligot.afriyan.Dto.CentrePartenaireDTO;
import org.ligot.afriyan.Dto.ServiceDTO;
import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.entities.CentrePartenaire;
import org.ligot.afriyan.entities.Status;
import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.mapper.CentrePartenaireMapper;
import org.ligot.afriyan.mapper.UtilisateurMapper;
import org.ligot.afriyan.repository.ICentrePartenaireRepository;
import org.ligot.afriyan.service.ICentrePartenaire;
import org.ligot.afriyan.service.IServiceEntity;
import org.ligot.afriyan.service.IUtilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CentrePartenaireImpl implements ICentrePartenaire {
    private final CentrePartenaireMapper mapper;
    private final ICentrePartenaireRepository repository;
    private final FileStorageService fileStorageService;
    private UtilisateurMapper utilisateurMapper;
    private final IUtilisateur utilisateur;
    private final IServiceEntity iServiceEntity;
    private final int PAGE_SIZE = 5;

    public CentrePartenaireImpl(CentrePartenaireMapper mapper, ICentrePartenaireRepository repository, FileStorageService fileStorageService, UtilisateurMapper utilisateurMapper, IUtilisateur utilisateur, IServiceEntity iServiceEntity) {
        this.mapper = mapper;
        this.repository = repository;
        this.fileStorageService = fileStorageService;
        this.utilisateurMapper = utilisateurMapper;
        this.utilisateur = utilisateur;
        this.iServiceEntity = iServiceEntity;
    }

    @Override
    public CentrePartenaireDTO findById(Long id) throws Exception {
        CentrePartenaire centrePartenaire = repository.findById(id).orElse(null);
        if(centrePartenaire == null){
            throw new Exception("L' USRAJ' que vous souhaitez modifier n'existes pas");
        }
        return findWithFile(centrePartenaire);
    }

    private ServiceDTO traitement(ServiceDTO serviceDTO){
        serviceDTO.setCentrePartenaire(null);
        serviceDTO.setProduits(new HashSet<>(0));
        serviceDTO.setDateCreation(null);
        return serviceDTO;
    }

    private CentrePartenaireDTO findWithFile(CentrePartenaire centrePartenaire){
        CentrePartenaireDTO centrePartenaireDTO = mapper.toDTO(centrePartenaire);
        Set<ServiceDTO> serviceDTOS = iServiceEntity.listServiceCP(centrePartenaire.getId()).stream().map(this::traitement).collect(Collectors.toSet());
        centrePartenaireDTO.setServiceOfferts(serviceDTOS);
        try {
            String[] elements = centrePartenaire.getPhoto().split(":");
            String imageBase64 = fileStorageService.convertImageToBase64(Constantes.CENTREPARTENAIREIMAGESUBPATH1+elements[0]);
            String image = "data:image/"+elements[1]+";base64,"+imageBase64;
            centrePartenaireDTO.setPhoto(image);
        }catch (Exception ex){
            centrePartenaireDTO.setPhoto(null);
        }
        return centrePartenaireDTO;
    }

    @Override
    @Transactional
    public CentrePartenaireDTO save(MultipartFile file, CentrePartenaireDTO centrePartenaireDTO) throws Exception {
        getUser();
        CentrePartenaire centrePartenaire = mapper.create(centrePartenaireDTO);
        if(repository.findCentrePartenaireByCreateur(centrePartenaire.getCreateur()).isPresent())
            throw new RuntimeException("L'utilisateur est deja administrateur d'une USRAJ");

        String name = fileStorageService.storeParagraphFileImage(file, Constantes.CENTREPARTENAIREIMAGESUBPATH);
        centrePartenaire.setPhoto(name);
        if(repository.findCentrePartenaireByTelephone(centrePartenaireDTO.getTelephone().trim()).isPresent())
            throw new Exception("Ce numero de telephone est deja utilise par une USRAJ");
        if(repository.findCentrePartenaireByNom(centrePartenaireDTO.getNom().trim()).isPresent())
            throw new Exception("Ce nom est deja utilise par une USRAJ");
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

    private double calculerDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Rayon de la Terre en km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // la distance en km
    }

    @Override
    public List<CentrePartenaireDTO> trouverCPProches(double userLat, double userLon) {
        boolean isTrue = true;
        double rayon = 10.0D;
        List<CentrePartenaireDTO> centrePartenaireDTOS = new ArrayList<>();
        List<CentrePartenaire> centrePartenaires = repository.findAll();
        while(isTrue){
            final double r = rayon;
            centrePartenaireDTOS = centrePartenaires.stream()
                    .filter(h -> calculerDistance(userLat, userLon, Double.valueOf(h.getLatittude()), Double.valueOf(h.getLongitude())) <= r)
                    .map(this::findWithFile)
                    .toList();
            if(!centrePartenaireDTOS.isEmpty()){
                isTrue = false;
            }
            if(rayon>200000D){
                isTrue = false;
            }
            rayon *=2;
        }

        return centrePartenaireDTOS;
    }

    @Override
    public List<CentrePartenaireDTO> listAll(){
        return repository.findAll().stream().map(this::findWithFile).toList();
    }

    @Override
    @Transactional
    public CentrePartenaireDTO update(CentrePartenaireDTO centrePartenaireDTO, Long id) throws Exception {
        CentrePartenaire centrePartenaire = repository.findById(id).orElse(null);
        if(centrePartenaire == null){
            throw new Exception("L' USRAJ' que vous souhaitez modifier n'existes pas");
        }
        centrePartenaireDTO.setId(id);
        mapper.update(centrePartenaireDTO, centrePartenaire);
        return mapper.toDTO(repository.save(mapper.create(centrePartenaireDTO)));
    }

    @Override
    public void updateUser(Long userId, Long idCP) throws Exception {
        UtilisateurDTO utilisateurDTO = utilisateur.findById(userId);
        CentrePartenaire centrePartenaire = repository.findById(idCP).orElseThrow(()->new Exception("USRAJ non trouver"));
        centrePartenaire.setCreateur(utilisateurMapper.create(utilisateurDTO));
        repository.save(centrePartenaire);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        CentrePartenaire centrePartenaire = repository.findById(id).orElseThrow(()->new Exception("not found"));
        centrePartenaire.setStatus(Status.INACTIVE);
        repository.save(centrePartenaire);
    }

    @Override
    public CentrePartenaireDTO findByUserId(Long id) throws Exception {/*
        UtilisateurDTO userDTO = utilisateur.findById(id);
        if(userDTO == null)
            throw new Exception("user with ID = "+id+" is null");*/
        Optional<CentrePartenaire> optionalCentrePartenaires = repository.findCentrePartenaireByCreateur(new Utilisateur(id));
        if(optionalCentrePartenaires.isEmpty())
            throw new RuntimeException("Pas d' USRAJ pour cet utilisateur");
        return findWithFile(optionalCentrePartenaires.get());
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
