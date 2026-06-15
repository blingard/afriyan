package org.ligot.afriyan.implement;

import org.ligot.afriyan.Dto.CycleMenstruelSettingsDTO;
import org.ligot.afriyan.Dto.PeriodLogDTO;
import org.ligot.afriyan.Dto.CycleProjectionDTO;
import org.ligot.afriyan.Dto.SimulationRequestDTO;
import org.ligot.afriyan.entities.CycleMenstruelSettings;
import org.ligot.afriyan.entities.PeriodLog;
import org.ligot.afriyan.entities.Sexe;
import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.mapper.CycleMenstruelMapper;
import org.ligot.afriyan.mapper.PeriodLogMapper;
import org.ligot.afriyan.repository.ICycleMenstruelSettingsRepository;
import org.ligot.afriyan.repository.IPeriodLogRepository;
import org.ligot.afriyan.service.ICycleMenstruelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class CycleMenstruelService implements ICycleMenstruelService {

    private static final Logger log = LoggerFactory.getLogger(CycleMenstruelService.class);

    private final ICycleMenstruelSettingsRepository settingsRepository;
    private final IPeriodLogRepository periodLogRepository;
    private final CycleMenstruelMapper settingsMapper;
    private final PeriodLogMapper periodLogMapper;
    private final UtilsService utilsService;
    private final TwilioService twilioService;

    public CycleMenstruelService(ICycleMenstruelSettingsRepository settingsRepository,
                                 IPeriodLogRepository periodLogRepository,
                                 CycleMenstruelMapper settingsMapper,
                                 PeriodLogMapper periodLogMapper,
                                 UtilsService utilsService,
                                 TwilioService twilioService) {
        this.settingsRepository = settingsRepository;
        this.periodLogRepository = periodLogRepository;
        this.settingsMapper = settingsMapper;
        this.periodLogMapper = periodLogMapper;
        this.utilsService = utilsService;
        this.twilioService = twilioService;
    }

    private void checkFeminin(Utilisateur user) throws IllegalAccessException {
        if (user.getSexe() != Sexe.FEMME) {
            throw new IllegalAccessException("Désolé, cette fonctionnalité est réservée uniquement aux personnes de sexe féminin.");
        }
    }

    private LocalDate toLocalDate(Date date) {
        if (date == null) return null;
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private Date toDate(LocalDate localDate) {
        if (localDate == null) return null;
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public List<CycleProjectionDTO> simulateCycle(SimulationRequestDTO dto) {
        if (dto.getDateDernieresRegles() == null) {
            throw new IllegalArgumentException("La date des dernières règles est requise pour la simulation.");
        }
        int dureeCycle = dto.getDureeCycleMoyenne() > 0 ? dto.getDureeCycleMoyenne() : 28;
        int dureeRegles = dto.getDureeReglesMoyenne() > 0 ? dto.getDureeReglesMoyenne() : 5;
        int nbCycles = dto.getNbCycles() > 0 ? dto.getNbCycles() : 3;

        LocalDate baseDate = toLocalDate(dto.getDateDernieresRegles());
        return generateProjections(baseDate, dureeCycle, dureeRegles, nbCycles);
    }

    @Override
    @Transactional
    public CycleMenstruelSettingsDTO saveSettings(CycleMenstruelSettingsDTO dto) throws Exception {
        Utilisateur user = utilsService.getUser();
        checkFeminin(user);

        Optional<CycleMenstruelSettings> existingOpt = settingsRepository.findByUtilisateur_Uuid(user.getUuid());
        CycleMenstruelSettings settings;
        if (existingOpt.isPresent()) {
            settings = existingOpt.get();
            settingsMapper.update(dto, settings);
        } else {
            settings = settingsMapper.create(dto);
            settings.setUtilisateur(user);
        }
        settings = settingsRepository.save(settings);
        return settingsMapper.toDTO(settings);
    }

    @Override
    public CycleMenstruelSettingsDTO getSettings() throws Exception {
        Utilisateur user = utilsService.getUser();
        checkFeminin(user);

        CycleMenstruelSettings settings = settingsRepository.findByUtilisateur_Uuid(user.getUuid())
                .orElseGet(() -> CycleMenstruelSettings.builder()
                        .dureeCycleMoyenne(28)
                        .dureeReglesMoyenne(5)
                        .smsAlerteActive(false)
                        .joursAvantAlerte(2)
                        .build());

        return settingsMapper.toDTO(settings);
    }

    @Override
    @Transactional
    public PeriodLogDTO addPeriodLog(PeriodLogDTO dto) throws Exception {
        Utilisateur user = utilsService.getUser();
        checkFeminin(user);

        if (dto.getDateDebut() == null) {
            throw new IllegalArgumentException("La date de début des règles est requise.");
        }

        PeriodLog log = periodLogMapper.create(dto);
        log.setUtilisateur(user);
        log = periodLogRepository.save(log);

        // Mettre à jour automatiquement la date des dernières règles dans les paramètres de cycle
        Optional<CycleMenstruelSettings> settingsOpt = settingsRepository.findByUtilisateur_Uuid(user.getUuid());
        if (settingsOpt.isPresent()) {
            CycleMenstruelSettings settings = settingsOpt.get();
            // Si le nouveau log est plus récent ou si aucune date n'est définie
            if (settings.getDateDernieresRegles() == null || log.getDateDebut().after(settings.getDateDernieresRegles())) {
                settings.setDateDernieresRegles(log.getDateDebut());
                settingsRepository.save(settings);
            }
        } else {
            // Créer des paramètres par défaut avec cette date
            CycleMenstruelSettings settings = CycleMenstruelSettings.builder()
                    .utilisateur(user)
                    .dateDernieresRegles(log.getDateDebut())
                    .dureeCycleMoyenne(28)
                    .dureeReglesMoyenne(5)
                    .smsAlerteActive(false)
                    .joursAvantAlerte(2)
                    .build();
            settingsRepository.save(settings);
        }

        return periodLogMapper.toDTO(log);
    }

    @Override
    public List<PeriodLogDTO> getPeriodLogs() throws Exception {
        Utilisateur user = utilsService.getUser();
        checkFeminin(user);

        List<PeriodLog> logs = periodLogRepository.findByUtilisateur_UuidOrderByDateDebutDesc(user.getUuid());
        return periodLogMapper.toDTOList(logs);
    }

    @Override
    public List<CycleProjectionDTO> getProjections(int numberCycles) throws Exception {
        Utilisateur user = utilsService.getUser();
        checkFeminin(user);

        CycleMenstruelSettings settings = settingsRepository.findByUtilisateur_Uuid(user.getUuid())
                .orElseThrow(() -> new IllegalArgumentException("Veuillez d'abord configurer vos paramètres de cycle menstruel."));

        if (settings.getDateDernieresRegles() == null) {
            throw new IllegalArgumentException("Veuillez renseigner la date de vos dernières règles dans vos paramètres ou ajouter un log de règles.");
        }

        LocalDate baseDate = toLocalDate(settings.getDateDernieresRegles());
        return generateProjections(baseDate, settings.getDureeCycleMoyenne(), settings.getDureeReglesMoyenne(), numberCycles);
    }

    private List<CycleProjectionDTO> generateProjections(LocalDate baseDate, int dureeCycle, int dureeRegles, int nbCycles) {
        List<CycleProjectionDTO> projections = new ArrayList<>();
        LocalDate today = LocalDate.now();

        LocalDate currentBase = baseDate;
        for (int i = 0; i < nbCycles; i++) {
            LocalDate startRegles = currentBase.plusDays(dureeCycle);
            LocalDate endRegles = startRegles.plusDays(dureeRegles - 1);
            LocalDate ovulation = startRegles.plusDays(dureeCycle - 14);
            LocalDate fertileStart = ovulation.minusDays(5);
            LocalDate fertileEnd = ovulation.plusDays(1);

            String phase = "FOLLICULAIRE"; // Default phase
            if (!today.isBefore(startRegles) && !today.isAfter(endRegles)) {
                phase = "REGLES";
            } else if (!today.isBefore(fertileStart) && !today.isAfter(fertileEnd)) {
                phase = "FERTILE";
            } else if (today.isAfter(fertileEnd) && today.isBefore(startRegles.plusDays(dureeCycle))) {
                phase = "LUTEALE";
            }

            projections.add(CycleProjectionDTO.builder()
                    .dateDebutRegles(toDate(startRegles))
                    .dateFinRegles(toDate(endRegles))
                    .dateOvulation(toDate(ovulation))
                    .debutPeriodeFertile(toDate(fertileStart))
                    .finPeriodeFertile(toDate(fertileEnd))
                    .phaseActuelle(phase)
                    .build());

            // Le prochain cycle se base sur la date de début calculée de ce cycle
            currentBase = startRegles;
        }

        return projections;
    }

    /**
     * Tâche automatisée planifiée pour l'envoi d'SMS
     * Exécutée quotidiennement à 8:00 AM
     */
    @Scheduled(cron = "0 0 8 * * ?")
    @Transactional(readOnly = true)
    public void sendScheduledSmsAlerts() {
        log.info("Démarrage de la tâche planifiée d'envoi d'alertes SMS de cycle...");
        List<CycleMenstruelSettings> allSettings = settingsRepository.findAll();
        LocalDate today = LocalDate.now();

        for (CycleMenstruelSettings settings : allSettings) {
            if (settings.isSmsAlerteActive() && settings.getDateDernieresRegles() != null) {
                Utilisateur user = settings.getUtilisateur();
                if (user == null || user.getTelephone() == null || user.getTelephone().trim().isEmpty()) {
                    continue;
                }

                // Calculer la prochaine période fertile
                LocalDate baseDate = toLocalDate(settings.getDateDernieresRegles());
                int dureeCycle = settings.getDureeCycleMoyenne();
                // Trouver le premier cycle futur dont la date de début de fertilité est après aujourd'hui
                LocalDate fertileStart = null;
                LocalDate currentBase = baseDate;

                for (int i = 0; i < 6; i++) { // Vérifier sur les 6 prochains cycles
                    LocalDate startRegles = currentBase.plusDays(dureeCycle);
                    LocalDate ovulation = startRegles.plusDays(dureeCycle - 14);
                    LocalDate cycleFertileStart = ovulation.minusDays(5);
                    if (cycleFertileStart.isAfter(today) || cycleFertileStart.isEqual(today)) {
                        fertileStart = cycleFertileStart;
                        break;
                    }
                    currentBase = startRegles;
                }

                if (fertileStart != null) {
                    long daysUntilFertile = ChronoUnit.DAYS.between(today, fertileStart);
                    if (daysUntilFertile == settings.getJoursAvantAlerte()) {
                        String message = String.format(
                                "Bonjour %s, votre période critique (fertile) débutera dans %d jours (le %s). Prenez vos précautions.",
                                user.getPrenom() != null ? user.getPrenom() : "",
                                daysUntilFertile,
                                fertileStart.toString()
                        );
                        try {
                            twilioService.sendOneSms(user.getTelephone().trim(), message);
                            log.info("SMS d'alerte envoyé avec succès à l'utilisateur : {} (Téléphone: {})", user.getUuid(), user.getTelephone());
                        } catch (Exception e) {
                            log.error("Erreur lors de l'envoi de l'alerte SMS à l'utilisateur {} : {}", user.getUuid(), e.getMessage());
                        }
                    }
                }
            }
        }
        log.info("Fin de la tâche planifiée d'envoi d'alertes SMS.");
    }
}
