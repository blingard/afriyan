package org.ligot.afriyan.learn.service.impl;

import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.learn.dto.UpdateProgressDTO;
import org.ligot.afriyan.learn.dto.UserFormationEnrollmentDTO;
import org.ligot.afriyan.learn.dto.UserProgressDTO;
import org.ligot.afriyan.learn.entities.Formation;
import org.ligot.afriyan.learn.entities.Modules;
import org.ligot.afriyan.learn.entities.UserFormationEnrollment;
import org.ligot.afriyan.learn.entities.UserProgress;
import org.ligot.afriyan.learn.repository.FormationRepository;
import org.ligot.afriyan.learn.repository.ModuleRepository;
import org.ligot.afriyan.learn.repository.UserFormationEnrollmentRepository;
import org.ligot.afriyan.learn.repository.UserProgressRepository;
import org.ligot.afriyan.learn.service.EnrollmentService;
import org.ligot.afriyan.repository.IUtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {

    private final UserFormationEnrollmentRepository enrollmentRepository;
    private final UserProgressRepository progressRepository;
    private final FormationRepository formationRepository;
    private final ModuleRepository moduleRepository;
    private final IUtilisateurRepository utilisateurRepo;

    public EnrollmentServiceImpl(UserFormationEnrollmentRepository enrollmentRepository, UserProgressRepository progressRepository, FormationRepository formationRepository, ModuleRepository moduleRepository, IUtilisateurRepository utilisateurRepo) {
        this.enrollmentRepository = enrollmentRepository;
        this.progressRepository = progressRepository;
        this.formationRepository = formationRepository;
        this.moduleRepository = moduleRepository;
        this.utilisateurRepo = utilisateurRepo;
    }

    @Override
    public UserFormationEnrollmentDTO enrollUser(Long userId, String formationId) {
        
        UUID id = UUID.fromString(formationId);
        // Vérifier si l'utilisateur est déjà inscrit
        var existing = enrollmentRepository.findByUserIdAndFormationId(userId, id);
        if (existing.isPresent()) {
            throw new RuntimeException("Utilisateur déjà inscrit à cette formation");
        }

        Utilisateur user = utilisateurRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        Formation formation = formationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));

        if (!formation.getStatus().equals(Formation.FormationStatus.PUBLISHED)) {
            throw new RuntimeException("Cette formation n'est pas encore publiée");
        }

        UserFormationEnrollment enrollment = new UserFormationEnrollment();
        enrollment.setUser(user);
        enrollment.setFormation(formation);
        enrollment.setStatus(UserFormationEnrollment.EnrollmentStatus.IN_PROGRESS);
        enrollment.setProgressionPourcent(0.0);
        enrollment.setDateDebut(new Date());
        enrollment.setDateInscription(new Date());

        UserFormationEnrollment saved = enrollmentRepository.save(enrollment);

        // Créer les entrées de progression pour chaque module
        List<Modules> modules = moduleRepository.findByFormationIdOrderByOrdre(id);
        for (Modules module : modules) {
            UserProgress progress = new UserProgress();
            progress.setEnrollment(saved);
            progress.setModule(module);
            progress.setStatus(UserProgress.ProgressStatus.NOT_STARTED);
            progress.setProgressionPourcent(0.0);
            progress.setDateCreation(new Date());
            progressRepository.save(progress);
        }

        return toEnrollmentDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public UserFormationEnrollmentDTO getEnrollmentById(String idEn) {
        UUID id = UUID.fromString(idEn);
        UserFormationEnrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscription non trouvée"));
        return toEnrollmentDTO(enrollment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserFormationEnrollmentDTO> getUserEnrollments(Long userId) {
        return enrollmentRepository.findByUserId(userId).stream()
                .map(this::toEnrollmentDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserFormationEnrollmentDTO> getFormationEnrollments(String formationId) {
        UUID id = UUID.fromString(formationId);
        return enrollmentRepository.findByFormationId(id).stream()
                .map(this::toEnrollmentDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserFormationEnrollmentDTO getUserEnrollmentForFormation(Long userId, String formationId) {
        
        UUID id = UUID.fromString(formationId);
        UserFormationEnrollment enrollment = enrollmentRepository.findByUserIdAndFormationId(userId, id)
                .orElseThrow(() -> new RuntimeException("Inscription non trouvée"));
        return toEnrollmentDTO(enrollment);
    }

    @Override
    public UserFormationEnrollmentDTO restartFormation(String enrollmentId) {
        UUID id = UUID.fromString(enrollmentId);
        UserFormationEnrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscription non trouvée"));

        // Réinitialiser l'inscription
        enrollment.setStatus(UserFormationEnrollment.EnrollmentStatus.IN_PROGRESS);
        enrollment.setProgressionPourcent(0.0);
        enrollment.setDateDebut(new Date());
        enrollment.setDateFin(null);
        enrollment.setScoreFinal(null);
        enrollment.setCertificatUrl(null);

        // Réinitialiser tous les progrès des modules
        List<UserProgress> progresses = progressRepository.findByEnrollmentId(id);
        for (UserProgress progress : progresses) {
            progress.setStatus(UserProgress.ProgressStatus.NOT_STARTED);
            progress.setProgressionPourcent(0.0);
            progress.setDateDebut(null);
            progress.setDateCompletion(null);
            progress.setScoreQuiz(null);
            progress.setQuizPassed(null);
            progressRepository.save(progress);
        }

        UserFormationEnrollment saved = enrollmentRepository.save(enrollment);
        return toEnrollmentDTO(saved);
    }

    @Override
    public UserFormationEnrollmentDTO abandonFormation(String enrollmentId) {
        UUID id = UUID.fromString(enrollmentId);
        UserFormationEnrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscription non trouvée"));
        
        enrollment.setStatus(UserFormationEnrollment.EnrollmentStatus.ABANDONED);
        enrollment.setDateModification(new Date());
        
        UserFormationEnrollment saved = enrollmentRepository.save(enrollment);
        return toEnrollmentDTO(saved);
    }

    @Override
    public UserProgressDTO updateModuleProgress(UpdateProgressDTO dto) {
        UserProgress progress = progressRepository.findByEnrollmentIdAndModuleId(
                dto.getEnrollmentId(), dto.getModuleId())
                .orElseThrow(() -> new RuntimeException("Progression non trouvée"));

        progress.setProgressionPourcent(dto.getProgressionPourcent());
        progress.setDateModification(new Date());

        if (progress.getStatus().equals(UserProgress.ProgressStatus.NOT_STARTED)) {
            progress.setStatus(UserProgress.ProgressStatus.IN_PROGRESS);
            progress.setDateDebut(new Date());
        }

        if (dto.getProgressionPourcent() >= 100.0) {
            progress.setStatus(UserProgress.ProgressStatus.COMPLETED);
            progress.setDateCompletion(new Date());
        }

        UserProgress saved = progressRepository.save(progress);
        
        // Mettre à jour la progression globale de l'inscription
        calculateAndUpdateEnrollmentProgress(dto.getEnrollmentId().toString());
        
        return toProgressDTO(saved);
    }

    @Override
    public UserProgressDTO startModule(String enrollmentId, String moduleId) {
        UUID idM = UUID.fromString(moduleId);
        UUID idEn = UUID.fromString(enrollmentId);
        UserProgress progress = progressRepository.findByEnrollmentIdAndModuleId(idEn, idM)
                .orElseThrow(() -> new RuntimeException("Progression non trouvée"));

        if (progress.getStatus().equals(UserProgress.ProgressStatus.NOT_STARTED)) {
            progress.setStatus(UserProgress.ProgressStatus.IN_PROGRESS);
            progress.setDateDebut(new Date());
            progress.setDateModification(new Date());
        }

        UserProgress saved = progressRepository.save(progress);
        return toProgressDTO(saved);
    }

    @Override
    public UserProgressDTO completeModule(String enrollmentId, String moduleId) {
        UUID idM = UUID.fromString(moduleId);
        UUID idEn = UUID.fromString(enrollmentId);
        UserProgress progress = progressRepository.findByEnrollmentIdAndModuleId(idEn, idM)
                .orElseThrow(() -> new RuntimeException("Progression non trouvée"));

        progress.setStatus(UserProgress.ProgressStatus.COMPLETED);
        progress.setProgressionPourcent(100.0);
        progress.setDateCompletion(new Date());
        progress.setDateModification(new Date());

        UserProgress saved = progressRepository.save(progress);
        
        // Mettre à jour la progression globale
        calculateAndUpdateEnrollmentProgress(enrollmentId);
        
        return toProgressDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserProgressDTO> getEnrollmentProgress(String enrollmentId) {
        UUID id = UUID.fromString(enrollmentId);
        return progressRepository.findByEnrollmentId(id).stream()
                .map(this::toProgressDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void calculateAndUpdateEnrollmentProgress(String id) {
        UUID enrollmentId = UUID.fromString(id);
        UserFormationEnrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Inscription non trouvée"));

        Double avgProgress = progressRepository.getAverageProgressByEnrollmentId(enrollmentId);
        enrollment.setProgressionPourcent(avgProgress != null ? avgProgress : 0.0);

        Long totalModules = moduleRepository.countByFormationId(enrollment.getFormation().getId());
        Long completedModules = progressRepository.countCompletedModulesByEnrollmentId(enrollmentId);

        // Si tous les modules sont terminés
        if (totalModules != null && completedModules != null && 
            totalModules.equals(completedModules) && totalModules > 0) {
            
            // Vérifier si un quiz final est requis
            if (enrollment.getFormation().getWithFinalQuiz()) {
                // Le statut sera mis à jour après le passage du quiz final
            } else {
                // Pas de quiz final, marquer comme complété
                enrollment.setStatus(UserFormationEnrollment.EnrollmentStatus.COMPLETED);
                enrollment.setDateFin(new Date());
            }
        }

        enrollmentRepository.save(enrollment);
    }

    private UserFormationEnrollmentDTO toEnrollmentDTO(UserFormationEnrollment enrollment) {
        UserFormationEnrollmentDTO dto = new UserFormationEnrollmentDTO();
        dto.setId(enrollment.getId());
        dto.setUserId(enrollment.getUser().getId());
        dto.setUserNom(enrollment.getUser().getNom());
        dto.setUserPrenom(enrollment.getUser().getPrenom());
        dto.setFormationId(enrollment.getFormation().getId());
        dto.setFormationTitre(enrollment.getFormation().getTitre());
        dto.setStatus(enrollment.getStatus().name());
        dto.setProgressionPourcent(enrollment.getProgressionPourcent());
        dto.setDateDebut(enrollment.getDateDebut());
        dto.setDateFin(enrollment.getDateFin());
        dto.setScoreFinal(enrollment.getScoreFinal());
        dto.setCertificatUrl(enrollment.getCertificatUrl());
        dto.setDateInscription(enrollment.getDateInscription());
        dto.setDateModification(enrollment.getDateModification());
        return dto;
    }

    private UserProgressDTO toProgressDTO(UserProgress progress) {
        UserProgressDTO dto = new UserProgressDTO();
        dto.setId(progress.getId());
        dto.setEnrollmentId(progress.getEnrollment().getId());
        dto.setModuleId(progress.getModule().getId());
        dto.setModuleTitre(progress.getModule().getTitre());
        dto.setStatus(progress.getStatus().name());
        dto.setProgressionPourcent(progress.getProgressionPourcent());
        dto.setDateDebut(progress.getDateDebut());
        dto.setDateCompletion(progress.getDateCompletion());
        dto.setScoreQuiz(progress.getScoreQuiz());
        dto.setQuizPassed(progress.getQuizPassed());
        dto.setDateCreation(progress.getDateCreation());
        dto.setDateModification(progress.getDateModification());
        return dto;
    }
}
