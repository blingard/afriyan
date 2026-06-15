package org.ligot.afriyan.implement;

import org.ligot.afriyan.Dto.PlanningFamilialDTO;
import org.ligot.afriyan.Dto.PlanningFamilialLogDTO;
import org.ligot.afriyan.entities.PlanningFamilial;
import org.ligot.afriyan.entities.PlanningFamilialLog;
import org.ligot.afriyan.entities.MethodePlanning;
import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.mapper.PlanningFamilialMapper;
import org.ligot.afriyan.mapper.PlanningFamilialLogMapper;
import org.ligot.afriyan.repository.IPlanningFamilialRepository;
import org.ligot.afriyan.repository.IPlanningFamilialLogRepository;
import org.ligot.afriyan.service.IPlanningFamilialService;
import org.ligot.afriyan.service.ICycleMenstruelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PlanningFamilialService implements IPlanningFamilialService {

    private final IPlanningFamilialRepository planningRepository;
    private final IPlanningFamilialLogRepository logRepository;
    private final PlanningFamilialMapper planningMapper;
    private final PlanningFamilialLogMapper logMapper;
    private final UtilsService utilsService;
    private final ICycleMenstruelService cycleMenstruelService;

    public PlanningFamilialService(IPlanningFamilialRepository planningRepository,
                                   IPlanningFamilialLogRepository logRepository,
                                   PlanningFamilialMapper planningMapper,
                                   PlanningFamilialLogMapper logMapper,
                                   UtilsService utilsService,
                                   ICycleMenstruelService cycleMenstruelService) {
        this.planningRepository = planningRepository;
        this.logRepository = logRepository;
        this.planningMapper = planningMapper;
        this.logMapper = logMapper;
        this.utilsService = utilsService;
        this.cycleMenstruelService = cycleMenstruelService;
    }

    @Override
    @Transactional
    public PlanningFamilialDTO savePlanning(PlanningFamilialDTO dto) throws Exception {
        Utilisateur user = utilsService.getUser();

        // Si l'utilisateur demande la synchronisation automatique pour la méthode naturelle,
        // on vérifie qu'il a bien configuré ses réglages de cycle menstruel.
        if (dto.isSynchroniserRegles() && dto.getMethode() == MethodePlanning.METHODE_NATURELLE) {
            try {
                cycleMenstruelService.getSettings();
            } catch (Exception e) {
                throw new IllegalArgumentException("Pour synchroniser vos règles, veuillez d'abord configurer vos paramètres de cycle menstruel.");
            }
        }

        Optional<PlanningFamilial> existingOpt = planningRepository.findByUtilisateur_Uuid(user.getUuid());
        PlanningFamilial planning;

        if (existingOpt.isPresent()) {
            planning = existingOpt.get();
            planningMapper.update(dto, planning);
        } else {
            planning = planningMapper.create(dto);
            planning.setUtilisateur(user);
        }

        planning = planningRepository.save(planning);
        return planningMapper.toDTO(planning);
    }

    @Override
    public PlanningFamilialDTO getPlanning() throws Exception {
        Utilisateur user = utilsService.getUser();
        PlanningFamilial planning = planningRepository.findByUtilisateur_Uuid(user.getUuid())
                .orElseGet(() -> PlanningFamilial.builder()
                        .methode(MethodePlanning.AUCUNE)
                        .rappelActif(false)
                        .synchroniserRegles(false)
                        .build());
        return planningMapper.toDTO(planning);
    }

    @Override
    @Transactional
    public PlanningFamilialLogDTO addLog(PlanningFamilialLogDTO dto) throws Exception {
        Utilisateur user = utilsService.getUser();
        PlanningFamilial planning = planningRepository.findByUtilisateur_Uuid(user.getUuid())
                .orElseThrow(() -> new IllegalArgumentException("Aucun planning familial configuré pour cet utilisateur. Veuillez d'abord en configurer un."));

        if (dto.getDateLog() == null) {
            throw new IllegalArgumentException("La date du journal de suivi est requise.");
        }

        PlanningFamilialLog log = logMapper.create(dto);
        log.setPlanningFamilial(planning);
        log = logRepository.save(log);

        return logMapper.toDTO(log);
    }

    @Override
    public List<PlanningFamilialLogDTO> getLogs() throws Exception {
        Utilisateur user = utilsService.getUser();
        List<PlanningFamilialLog> logs = logRepository.findByPlanningFamilial_Utilisateur_UuidOrderByDateLogDesc(user.getUuid());
        return logMapper.toDTOList(logs);
    }
}
