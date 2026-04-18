package org.ligot.afriyan.echo.service;

import jakarta.transaction.Transactional;

import org.ligot.afriyan.Dto.PageDTO;
import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.echo.AlertStatus;
import org.ligot.afriyan.echo.ValidationRole;
import org.ligot.afriyan.echo.dto.AlertUserDto;
import org.ligot.afriyan.echo.dto.AlertsDTO;
import org.ligot.afriyan.echo.dto.IndicatorData;
import org.ligot.afriyan.echo.entities.*;
import org.ligot.afriyan.echo.mapper.AlertMapper;
import org.ligot.afriyan.echo.mapper.LocationMapper;
import org.ligot.afriyan.echo.repo.*;
import org.ligot.afriyan.entities.Groupes;
import org.ligot.afriyan.entities.Roles;
import org.ligot.afriyan.entities.Status;
import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.implement.UtilsService;
import org.ligot.afriyan.init.RolesName;
import org.ligot.afriyan.mapper.UtilisateurMapper;
import org.ligot.afriyan.repository.IUtilisateurRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class AlertValidationService {

    private final AlertRepository alertRepository;
    private final NotificationService notificationService;
    private final UtilsService utilsService;
    private final IUtilisateurRepository repository;
    private final UtilisateurMapper utilisateurMapper;
    private final CrppRepository crppRepository;
    private final MaireRepo maireRepo;
    private final PrefetRepo prefetRepo;
    private final AlertMapper mapper;
    private final LocationMapper locationMapper;
    private final AlertRiskTypeRepository alertRiskTypeRepository;
    private final LocalityRepo localityRepo;


    public AlertValidationService(AlertRepository alertRepository, NotificationService notificationService,
                                  UtilsService utilsService, IUtilisateurRepository repository, UtilisateurMapper utilisateurMapper, CrppRepository crppRepository,
                                  MaireRepo maireRepo, PrefetRepo prefetRepo,
                                  AlertMapper mapper, LocationMapper locationMapper, AlertRiskTypeRepository alertRiskTypeRepository,
                                  LocalityRepo localityRepo) {
        this.alertRepository = alertRepository;
        this.notificationService = notificationService;
        this.utilsService = utilsService;
        this.repository = repository;
        this.utilisateurMapper = utilisateurMapper;
        this.crppRepository = crppRepository;
        this.maireRepo = maireRepo;
        this.prefetRepo = prefetRepo;
        this.mapper = mapper;
        this.locationMapper = locationMapper;
        this.alertRiskTypeRepository = alertRiskTypeRepository;
        this.localityRepo = localityRepo;
    }

    @Transactional
    public void submitAlert(AlertsDTO alertsDTO) {
        final Utilisateur utilisateur = utilsService.getUser();
        AlertRiskType riskType = alertRiskTypeRepository.findAllById(alertsDTO.getRiskType().getId()).orElseThrow(()->new RuntimeException("Alert risk type n'existe pas"));
        Localities localities = localityRepo.findById(alertsDTO.getLocalities().getId()).orElseThrow(()->new RuntimeException("Locality n'existe pas"));
        Alerts alert = mapper.create(alertsDTO);
        alert.setId(null);
        alert.setReporterUserId(utilisateur.getId().toString());
        alert.setStatus(AlertStatus.REPORTED);
        alert.setCreatedAt(LocalDateTime.now());
        alert.setLastUpdatedAt(LocalDateTime.now());
        alert.setRiskType(riskType);
        alert.setLocalities(localities);
        alert.setCurrentValidatorRole(ValidationRole.CCPR_COMMITTEE.name()); // Première étape de validation par le CCPR [67]
        Alerts savedAlert = alertRepository.save(alert);
        UUID communeId = savedAlert.getLocalities().getCommune().getId();

        CompletableFuture.runAsync(() -> {
            try {
                List<Utilisateur> utilisateurs = crppRepository.findAllByUserOfCrpp(communeId);
                if(utilisateurs.isEmpty())
                    throw new RuntimeException("User list is null");
                Set<String> phoneNumber = utilisateurs.stream().map(Utilisateur::getTelephone).collect(Collectors.toSet());
                String message = "Alert: Rapporte dans la localite de "+alert.getLocalities().getName()+
                        " (Commune: "+alert.getLocalities().getCommune().getName()+", Departement: "+alert.getLocalities().getCommune().getDepartement().getName()+")";
                notificationService.notifyCSPRForReview(phoneNumber, message); // Notifier le CCPR
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });
    }

    private RolesName getPriorityRole(Groupes groupes){
        if(groupes==null)
            throw new RuntimeException("Vous n'etes pas authorise.");
        if(groupes.getRoles()==null)
            throw new RuntimeException("Vous n'etes pas authorise.");
        if(groupes.getRoles().isEmpty())
            throw new RuntimeException("Vous n'etes pas authorise.");
        List<Roles> rolesList = groupes.getRoles().stream().toList();
        List<RolesName> rolesNames = rolesList.stream().map(roles -> RolesName.valueOf(roles.getNom())).toList();
        if(rolesNames.isEmpty())
            throw new RuntimeException("Vous n'etes pas authorise.");
        if(rolesNames.contains(RolesName.SUPERADMIN))
            return RolesName.SUPERADMIN;
        if(rolesNames.contains(RolesName.ROOT))
            return RolesName.ROOT;
        if(rolesNames.contains(RolesName.ADMIN))
            return RolesName.ADMIN;
        if(rolesNames.contains(RolesName.LOCAL_AUTHORITY))
            return RolesName.LOCAL_AUTHORITY;
        if(rolesNames.contains(RolesName.MAIRE))
            return RolesName.MAIRE;
        if(rolesNames.contains(RolesName.CCPR_COMMITTEE))
            return RolesName.CCPR_COMMITTEE;
        if(rolesNames.contains(RolesName.COMMUNITY_COMMITTEE))
            return RolesName.COMMUNITY_COMMITTEE;
        else
            throw new RuntimeException("Vous n'etes pas authorise.");
    }

    @Transactional
    public Alerts validateAlert(UUID alertId, boolean approved, String comments) {
        Utilisateur utilisateur = utilsService.getUser();
        RolesName role = getPriorityRole(utilisateur.getGroupe());
        Alerts alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alerte non trouvée"));
        if(alert.getStatus().equals(AlertStatus.REJECTED) || alert.getStatus().equals(AlertStatus.RESOLVED) ||
                alert.getStatus().equals(AlertStatus.DISMISSED) || alert.getStatus().equals(AlertStatus.ALERT_ACTIVE))
            throw new RuntimeException("Alerte deja en status final");
        alert.setValidationComments(comments);
        alert.setLastUpdatedAt(LocalDateTime.now());

        if (approved) {
            // Logique de progression de la validation en fonction de la nature du risque et du rôle
            switch (role) {
                case CCPR_COMMITTEE: {
                    // Le CCPR a validé [67]. Maintenant, ça remonte aux autorités [70, 114]
                    alert.setStatus(AlertStatus.PENDING_AUTHORITY_APPROVAL);
                    alert.setCurrentValidatorRole(ValidationRole.LOCAL_AUTHORITY.name());
                    final Communes commune = alert.getLocalities().getCommune();
                    CompletableFuture.runAsync(() -> {
                        try {
                            List<Utilisateur> utilisateurs = new ArrayList<>();
                            List<Maire> maire = maireRepo.findAllByCommuneAndActive(commune, true);
                            if (!maire.isEmpty())
                                utilisateurs = maire.stream().map(Maire::getUtilisateur).toList();
                            List<Prefet> prefets = prefetRepo.findAllByDepartementAndActive(commune.getDepartement(), true);
                            if (!prefets.isEmpty())
                                utilisateurs = prefets.stream().map(Prefet::getUtilisateur).toList();
                            if (utilisateurs.isEmpty())
                                throw new RuntimeException("User list is null");
                            Set<String> phoneNumber = utilisateurs.stream().map(Utilisateur::getTelephone).collect(Collectors.toSet());
                            String message = "Alert: en attente de validation dans la localite de " + alert.getLocalities().getName() +
                                    " (Commune: " + alert.getLocalities().getCommune().getName() + ", Departement: " + alert.getLocalities().getCommune().getDepartement().getName() + ")";
                            notificationService.notifyCSPRForReview(phoneNumber, message); // Notifier le Authorite
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    });
                    break;
                }
                case SUPERADMIN, ROOT, ADMIN, LOCAL_AUTHORITY, MAIRE: {
                    // Les autorités ont validé [70, 114]. L'alerte est active.
                    alert.setStatus(AlertStatus.ALERT_ACTIVE);
                    alert.setCurrentValidatorRole(null); // Plus de validateur requis
                    final Communes communes = alert.getLocalities().getCommune();
                    CompletableFuture.runAsync(() -> {
                        try {
                            int page = 0;
                            int size = 100;
                            Page<Utilisateur> utilisateurs = repository.findAllByCommunes_IdAndStatus(communes.getId(), Status.ACTIVE, PageRequest.of(page, size));

                            while (!utilisateurs.isEmpty()) {
                                Set<String> phoneNumber = utilisateurs.stream().map(Utilisateur::getTelephone).collect(Collectors.toSet());
                                String message = "Alert: dans la localite de " + alert.getLocalities().getName() +
                                        " (Commune: " + alert.getLocalities().getCommune().getName() + ", Departement: " + alert.getLocalities().getCommune().getDepartement().getName() + ")";
                                notificationService.notifyCSPRForReview(phoneNumber, message); // Notifier le Authorite
                                page = page + 1;
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                    break;
                }
                default:
                    throw new RuntimeException("Validation non supportée pour ce rôle à cette étape.");
            }
        } else {
            // Rejet de l'alerte
            switch (role) {
                case SUPERADMIN, ROOT, ADMIN, CCPR_COMMITTEE, LOCAL_AUTHORITY, MAIRE:
                    alert.setStatus(AlertStatus.REJECTED);
                    alert.setCurrentValidatorRole(null); // Plus de validateur requis
                    notificationService.notifyReporterOfRejection(alert);
                    break;
                default:
                    throw new RuntimeException("Validation non supportée pour ce rôle à cette étape.");
            }
        }
        return alertRepository.save(alert);
    }
    public AlertUserDto getAlert(UUID alertId) {
        Alerts alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alerte non trouvée"));
        Utilisateur utilisateur = utilsService.getUserById(Long.valueOf(alert.getReporterUserId()));
        UtilisateurDTO utilisateurDTO = utilisateurMapper.toDTO(utilisateur);
        AlertsDTO alertsDTO = mapper.toDTO(alert);

        return new AlertUserDto(alertsDTO, utilisateurDTO);
    }

    @Transactional
    public void evaluateIndicators(IndicatorData data) {
        AlertRiskType riskType = alertRiskTypeRepository.findAllById(data.getRiskType().getId()).orElseThrow(()->new RuntimeException("Alert risk type n'existe pas"));
        Alerts newAlert = new Alerts();
        newAlert.setLocalities(locationMapper.create(data.getLocalities()));
        newAlert.setRiskType(riskType); //setRiskType("INONDATION");
        newAlert.setDescription("Niveau de pluie ou du fleuve critique détecté automatiquement.");
        newAlert.setStatus(AlertStatus.REPORTED);
        newAlert.setCurrentValidatorRole(ValidationRole.CCPR_COMMITTEE.name());
        Alerts savedAlert = alertRepository.save(newAlert);


        /*switch (data.getRiskType().getCode()){
            case AlertRiskType.FLOODING -> {
                if ((data.getRainfall() != null && data.getRainfall() >= 100) ||
                        (data.getRiverLevel() != null && data.getRiverLevel() >= 6.5)) {
                    Alerts newAlert = new Alerts();
                    newAlert.setLocalities(locationMapper.create(data.getLocalities()));
                    newAlert.setRiskType(data.getRiskType()); //setRiskType("INONDATION");
                    newAlert.setDescription("Niveau de pluie ou du fleuve critique détecté automatiquement.");
                    newAlert.setStatus(AlertStatus.REPORTED);
                    newAlert.setCurrentValidatorRole(ValidationRole.CCPR_COMMITTEE.name());
                    Alerts savedAlert = alertRepository.save(newAlert);
                    //notificationService.notifyCSPRForReview(savedAlert);
                }
                break;
            }
            case DROUGHT -> {
                if (data.getDaysWithoutRain() != null && data.getDaysWithoutRain() > 14) {
                    Alerts droughtAlert = new Alerts();
                    droughtAlert.setRiskType(AlertRiskType.DROUGHT);
                    droughtAlert.setLocalities(locationMapper.create(data.getLocalities()));
                    droughtAlert.setDescription("Sécheresse prolongée détectée automatiquement.");
                    droughtAlert.setStatus(AlertStatus.REPORTED);
                    droughtAlert.setCurrentValidatorRole(ValidationRole.CCPR_COMMITTEE.name());
                    Alerts savedAlert = alertRepository.save(droughtAlert);
                    //notificationService.notifyCSPRForReview(savedAlert);
                }
                break;
            }
            case CONFLICT -> {
                if (data.getNumberOfConflicts() != null && data.getNumberOfConflicts() > 2) {
                    Alerts conflictAlert = new Alerts();
                    conflictAlert.setRiskType(AlertRiskType.CONFLICT);
                    conflictAlert.setLocalities(locationMapper.create(data.getLocalities()));
                    conflictAlert.setDescription("Multiples incidents signalés - Risque de conflit élevé.");
                    conflictAlert.setStatus(AlertStatus.REPORTED);
                    conflictAlert.setCurrentValidatorRole(ValidationRole.CCPR_COMMITTEE.name());
                    Alerts savedAlert = alertRepository.save(conflictAlert);
                    //notificationService.notifyCSPRForReview(savedAlert);
                }
            }
        }*/
    }

    public PageDTO<AlertsDTO> listAll(int page, int size, String idRiskType) {
        Utilisateur utilisateur = utilsService.getUser();
        RolesName role = getPriorityRole(utilisateur.getGroupe());
        AlertRiskType riskType = alertRiskTypeRepository.findAllById(UUID.fromString(idRiskType)).orElseThrow(()->new RuntimeException("Alert risk type n'existe pas"));
        PageRequest pageRequest = PageRequest.of(page, size);
        switch (role) {
            case CCPR_COMMITTEE, COMMUNITY_COMMITTEE -> {
                return getAlert(riskType, AlertStatus.REPORTED, pageRequest);
            }
            case LOCAL_AUTHORITY, MAIRE -> {
                return getAlert(riskType, AlertStatus.PENDING_AUTHORITY_APPROVAL, pageRequest);
            }
            case SUPERADMIN, ADMIN, ROOT -> {
                return getAllAlert(riskType, pageRequest);
            }
            default -> throw new RuntimeException("Vous n'etes pas authorise.");
        }
    }

    public PageDTO<AlertsDTO> listAllPublic(int page, int size, String idRiskType) {
        AlertRiskType riskType = alertRiskTypeRepository.findAllById(UUID.fromString(idRiskType)).orElseThrow(()->new RuntimeException("Alert risk type n'existe pas"));
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Alerts> alerts = alertRepository.findAlertsByRiskTypeAndStatus(riskType, AlertStatus.ALERT_ACTIVE, pageRequest);
        return new PageDTO<>(
                new PageImpl<>(
                        alerts.stream().map(mapper::toDTO).collect(Collectors.toList()),
                        pageRequest,
                        alerts.getTotalElements()
                )
        );
    }

    public long count(String type) {
        Utilisateur utilisateur = utilsService.getUser();
        RolesName role = getPriorityRole(utilisateur.getGroupe());
        AlertRiskType riskType = alertRiskTypeRepository.findAllById(UUID.fromString(type)).orElseThrow(()->new RuntimeException("Alert risk type n'existe pas"));
        switch (role) {
            case CCPR_COMMITTEE, COMMUNITY_COMMITTEE -> {
                return alertRepository.countAlertsByRiskTypeAndStatus(riskType, AlertStatus.REPORTED);
            }
            case LOCAL_AUTHORITY, MAIRE -> {
                return alertRepository.countAlertsByRiskTypeAndStatus(riskType, AlertStatus.PENDING_AUTHORITY_APPROVAL);
            }
            case SUPERADMIN, ADMIN, ROOT -> {
                return alertRepository.countAlertsByRiskType(riskType);
            }
            default -> throw new RuntimeException("Vous n'etes pas authorise.");
        }
    }

    public long countPublic(String type) {
        AlertRiskType riskType = alertRiskTypeRepository.findAllById(UUID.fromString(type)).orElseThrow(()->new RuntimeException("Alert risk type n'existe pas"));
        return alertRepository.countAlertsByRiskTypeAndStatus(riskType, AlertStatus.ALERT_ACTIVE);
    }


    private PageDTO<AlertsDTO> getAlert(AlertRiskType riskType,  AlertStatus status, PageRequest pageRequest){
        Page<Alerts> alerts = alertRepository.findAlertsByRiskTypeAndStatus(riskType, status, pageRequest);
        return new PageDTO<>(
                new PageImpl<>(
                        alerts.stream().map(mapper::toDTO).collect(Collectors.toList()),
                        pageRequest,
                        alerts.getTotalElements()
                )
        );
    }

    private PageDTO<AlertsDTO> getAllAlert(AlertRiskType riskType, PageRequest pageRequest){
        Page<Alerts> alerts = alertRepository.findAlertsByRiskType(riskType, pageRequest);
        return new PageDTO<>(
                new PageImpl<>(
                        alerts.stream().map(mapper::toDTO).collect(Collectors.toList()),
                        pageRequest,
                        alerts.getTotalElements()
                )
        );
    }

    /*private String getStatusMessage(AlertRiskType riskType){
        return switch (riskType){
            case CONFLICT -> "Risque de conflit";
            case DROUGHT -> "Risque de secheresse";
            case FLOODING -> "Risque d'innondation";
            case GBV -> "Risque de Violence base sur le Genre";
            case EPIDEMIC -> "Risque d'epidemie";
            case ENVIRONMENTAL -> "Risque environnemental";
            case TECHNOLOGICAL -> "Risque Technologique";
        };
    }*/
}
