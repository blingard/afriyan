package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Constantes;
import org.ligot.afriyan.Dto.*;
import org.ligot.afriyan.entities.*;
import org.ligot.afriyan.init.PermissionEnum;
import org.ligot.afriyan.mapper.CentrePartenaireMapper;
import org.ligot.afriyan.mapper.UtilisateurMapper;
import org.ligot.afriyan.repository.ICentrePartenaireRepository;
import org.ligot.afriyan.service.ICentrePartenaire;
import org.ligot.afriyan.service.IServiceEntity;
import org.ligot.afriyan.service.IUtilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
    private final IUtilisateur iUtilisateur;
    private final IServiceEntity iServiceEntity;
    private final UtilsService utilsService;
    private final int PAGE_SIZE = 5;

    public CentrePartenaireImpl(CentrePartenaireMapper mapper, ICentrePartenaireRepository repository,
                                FileStorageService fileStorageService, UtilisateurMapper utilisateurMapper, IUtilisateur iUtilisateur,
                                IServiceEntity iServiceEntity, UtilsService utilsService) {
        this.mapper = mapper;
        this.repository = repository;
        this.fileStorageService = fileStorageService;
        this.utilisateurMapper = utilisateurMapper;
        this.iUtilisateur = iUtilisateur;
        this.iServiceEntity = iServiceEntity;
        this.utilsService = utilsService;
    }

    @Override
    //@org.springframework.cache.annotation.Cacheable(value = "centrePartenaire", key = "#id")
    public CentrePartenaireDTO findById(Long id) throws Exception {
        CentrePartenaire centrePartenaire = repository.findById(id).orElse(null);
        if (centrePartenaire == null) {
            throw new Exception("L' USRAJ' que vous souhaitez modifier n'existes pas");
        }
        return findWithFile(centrePartenaire);
    }

    @Override
    public CentrePartenaireSMARTDTO findByIdClient(Long id) throws Exception {
        CentrePartenaire centrePartenaire = repository.findByIdAndStatus(id, Status.ACTIVE).orElse(null);
        if (centrePartenaire == null) {
            throw new Exception("L' USRAJ' que vous souhaitez modifier n'existes pas");
        }
        return findWithFileSmart(centrePartenaire);
    }

    private ServiceDTO traitement(ServiceEntity serviceEntity) {
        ServiceDTO dto = new ServiceDTO();
        dto.setLibelle(serviceEntity.getLibelle());
        dto.setId(serviceEntity.getId());
        dto.setDescription(serviceEntity.getDescription());
        Set<ProduitDTO> produitDTOS = new HashSet<>(0);
        dto.setDateCreation(serviceEntity.getDateCreation());
        if(!serviceEntity.getProduits().isEmpty()){
            produitDTOS = serviceEntity.getProduits().stream().map(this::traitement).collect(Collectors.toSet());
        }
        dto.setProduits(produitDTOS);
        return dto;
    }

    private ProduitDTO traitement(Produit produit) {
        ProduitDTO dto = new ProduitDTO();
        dto.setNom(produit.getNom());
        dto.setLibelle(produit.getLibelle());
        dto.setId(produit.getId());
        dto.setDescription(produit.getDescription());
        dto.setPrix(produit.getPrix());
        dto.setActive(produit.isActive());
        dto.setDateCreation(produit.getDatCreation());
        return dto;
    }

    private CentrePartenaireDTO findWithFile(CentrePartenaire centrePartenaire) {
        CentrePartenaireDTO centrePartenaireDTO = mapper.toDTO(centrePartenaire);
        Set<ServiceDTO> serviceDTOS = iServiceEntity.listServiceCPEntity(centrePartenaire.getId()).stream()
                .map(this::traitement).collect(Collectors.toSet());
        centrePartenaireDTO.setServiceOfferts(serviceDTOS);
        return centrePartenaireDTO;
    }

    private CentrePartenaireSMARTDTO findWithFileSmart(CentrePartenaire centrePartenaire) {

        CentrePartenaireSMARTDTO centrePartenaireDTO = mapper.toDTOSmart(centrePartenaire);

        Set<ServiceDTO> serviceDTOS = iServiceEntity
                .listServiceCPEntity(centrePartenaire.getId())
                .stream()
                .map(this::traitement)
                .peek(serviceDTO -> {
                    Set<ProduitDTO> produitsActifs = serviceDTO.getProduits()
                            .stream()
                            .filter(ProduitDTO::isActive)
                            .collect(Collectors.toSet());

                    serviceDTO.setProduits(produitsActifs);
                })
                .filter(serviceDTO -> !serviceDTO.getProduits().isEmpty())
                .collect(Collectors.toSet());

        centrePartenaireDTO.setServiceOfferts(serviceDTOS);

        return centrePartenaireDTO;
    }

    private CentrePartenaireSMARTDTO findWithFileSmartAdmin(CentrePartenaire centrePartenaire) {

        CentrePartenaireSMARTDTO centrePartenaireDTO = mapper.toDTOSmart(centrePartenaire);

        Set<ServiceDTO> serviceDTOS = iServiceEntity
                .listServiceCPEntity(centrePartenaire.getId())
                .stream()
                .map(this::traitement)
                .collect(Collectors.toSet());

        centrePartenaireDTO.setServiceOfferts(serviceDTOS);

        return centrePartenaireDTO;
    }

    private boolean filterActive(CentrePartenaire centrePartenaire){

        Set<ServiceDTO> serviceDTOS = iServiceEntity
                .listServiceCPEntity(centrePartenaire.getId())
                .stream()
                .map(this::traitement)
                .collect(Collectors.toSet());

        return serviceDTOS.stream()
                .anyMatch(serviceDTO ->
                        serviceDTO.getProduits() != null
                                && !serviceDTO.getProduits().isEmpty()
                                && serviceDTO.getProduits()
                                .stream()
                                .anyMatch(ProduitDTO::isActive)
                );
    }

    @Override
    @Transactional
    public CentrePartenaireDTO save(MultipartFile file, CentrePartenaireDTO centrePartenaireDTO) throws Exception {
        getUser();
        CentrePartenaire centrePartenaire = mapper.create(centrePartenaireDTO);
        // if(repository.findCentrePartenaireByCreateur(centrePartenaire.getCreateur()).isPresent())
        // throw new RuntimeException("L'utilisateur est deja administrateur d'une
        // USRAJ");

        String name = fileStorageService.storeParagraphFileImage(file, Constantes.CENTREPARTENAIREIMAGESUBPATH);
        centrePartenaire.setPhoto(name);
        if (repository.findCentrePartenaireByTelephone(centrePartenaireDTO.getTelephone().trim()).isPresent())
            throw new Exception("Ce numero de telephone est deja utilise par une USRAJ");
        if (repository.findCentrePartenaireByNom(centrePartenaireDTO.getNom().trim()).isPresent())
            throw new Exception("Ce nom est deja utilise par une USRAJ");
        return mapper.toDTO(repository.save(centrePartenaire));
    }

    private void getUser() {
        utilsService.getUser();
    }

    @Override
    public Page<CentrePartenaireDTO> list(int page) throws Exception {
        Page<CentrePartenaire> pages = repository.findAll(PageRequest.of(page, PAGE_SIZE));
        return new PageImpl<>(pages.map(this::findWithFile).toList(), PageRequest.of(page, PAGE_SIZE),
                pages.getTotalElements());
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
        while (isTrue) {
            final double r = rayon;
            centrePartenaireDTOS = centrePartenaires.stream()
                    .filter(h -> calculerDistance(userLat, userLon, Double.valueOf(h.getLatittude()),
                            Double.valueOf(h.getLongitude())) <= r)
                    .filter(this::filterActive)
                    .map(this::findWithFile)
                    .toList();
            if (!centrePartenaireDTOS.isEmpty()) {
                isTrue = false;
            }
            if (rayon > 200000D) {
                isTrue = false;
            }
            rayon *= 2;
        }

        return centrePartenaireDTOS;
    }

    @Override
    public List<CentrePartenaireDTO> listAll() {
        return repository.findAll().stream().map(this::findWithFile).toList();
    }

    @Override
    public List<CentrePartenaireSMARTDTO> listAllUser() throws Exception {
        return repository.findCentrePartenaireByStatus(Status.ACTIVE)
                .stream()
                .filter(this::filterActive)
                .map(this::findWithFileSmart)
                .toList();
    }

    @Override
    @Transactional
    public CentrePartenaireDTO update(CentrePartenaireDTO centrePartenaireDTO, Long id) throws Exception {
        CentrePartenaire centrePartenaire = repository.findById(id).orElse(null);
        if (centrePartenaire == null) {
            throw new Exception("L' USRAJ' que vous souhaitez modifier n'existes pas");
        }
        centrePartenaireDTO.setId(id);
        mapper.update(centrePartenaireDTO, centrePartenaire);
        return mapper.toDTO(repository.save(mapper.create(centrePartenaireDTO)));
    }

    @Override
    public Set<CentrePartenaireSMARTDTO> usrajAdmin() throws Exception {
        Utilisateur utilisateur = utilsService.getUser();
        Optional<Set<CentrePartenaire>> centrePartenaire = repository.findCentrePartenaireByCreateurAndStatus(utilisateur, Status.ACTIVE);
        if(centrePartenaire.isEmpty())
            return new HashSet<>(0);
        return centrePartenaire.get().stream().map(this::findWithFileSmartAdmin).collect(Collectors.toSet());
    }

    @Override
    public void updateUser(Long userId, Long idCP) throws Exception {
        UtilisateurDTO utilisateurDTO = iUtilisateur.findById(userId);
        CentrePartenaire centrePartenaire = repository.findById(idCP)
                .orElseThrow(() -> new Exception("USRAJ non trouver"));
        centrePartenaire.setCreateur(utilisateurMapper.create(utilisateurDTO));
        repository.save(centrePartenaire);
        Utilisateur utilisateur = utilsService.getUserById(userId);
        utilisateur.getPermissionsAdd().add(PermissionEnum.CREATE_SERVICE);
        utilisateur.getPermissionsAdd().add(PermissionEnum.CREATE_PRODUCT);
        utilisateur.getPermissionsAdd().add(PermissionEnum.UPDATE_SERVICE);
        utilisateur.getPermissionsAdd().add(PermissionEnum.UPDATE_PRODUCT);
        iUtilisateur.save(utilisateur);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        CentrePartenaire centrePartenaire = repository.findById(id).orElseThrow(() -> new Exception("not found"));
        centrePartenaire.setStatus(Status.INACTIVE);
        repository.save(centrePartenaire);
    }

    @Override
    //@org.springframework.cache.annotation.Cacheable(value = "centrePartenaireByUserId", key = "#id")
    public CentrePartenaireDTO findByUserId(Long id) throws Exception {

            throw new RuntimeException("Pas d' USRAJ pour cet utilisateur");
        //return findWithFile(optionalCentrePartenaires.get());
    }

    @Override
    public void active(Long id) {
        CentrePartenaire centrePartenaire = repository.findById(id).orElse(null);
        if (centrePartenaire != null) {
            centrePartenaire.setStatus(centrePartenaire.getStatus() == Status.ACTIVE ? Status.INACTIVE : Status.ACTIVE);
            repository.save(centrePartenaire);
        }
    }

    @Override
    public List<CentrePartenaireSMARTDTO> trouverCPProchesPublic(double userLat, double userLon) {
        boolean isTrue = true;
        double rayon = 10.0D;
        List<CentrePartenaireSMARTDTO> centrePartenaireDTOS = new ArrayList<>();
        List<CentrePartenaire> centrePartenaires = repository.findCentrePartenaireByStatus(Status.ACTIVE);
        while (isTrue) {
            final double r = rayon;
            centrePartenaireDTOS = centrePartenaires.stream()
                    .filter(h -> calculerDistance(userLat, userLon, Double.valueOf(h.getLatittude()),
                            Double.valueOf(h.getLongitude())) <= r)
                    .filter(this::filterActive)
                    .map(this::findWithFileSmart)
                    .toList();
            if (!centrePartenaireDTOS.isEmpty()) {
                isTrue = false;
            }
            if (rayon > 200000D) {
                isTrue = false;
            }
            rayon *= 2;
        }

        return centrePartenaireDTOS;
    }
}
