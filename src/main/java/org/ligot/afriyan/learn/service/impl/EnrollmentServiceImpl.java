package org.ligot.afriyan.learn.service.impl;

import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.implement.UtilsService;
import org.ligot.afriyan.learn.dto.*;
import org.ligot.afriyan.learn.entities.*;
import org.ligot.afriyan.learn.repository.*;
import org.ligot.afriyan.learn.service.EnrollmentService;
import org.ligot.afriyan.repository.IUtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {

    private final UserFormationEnrollmentRepository enrollmentRepository;
    private final UserProgressRepository progressRepository;
    private final FormationRepository formationRepository;
    private final ModuleRepository moduleRepository;
    private final ChapitreRepository chapitreRepository;
    private final UserChapterProgressRepository chapterProgressRepository;
    private final UserQuizAttemptRepository quizAttemptRepository;
    private final IUtilisateurRepository utilisateurRepo;
    private final UtilsService utilsService;

    public EnrollmentServiceImpl(UserFormationEnrollmentRepository enrollmentRepository, 
                                 UserProgressRepository progressRepository, 
                                 FormationRepository formationRepository, 
                                 ModuleRepository moduleRepository,
                                 ChapitreRepository chapitreRepository,
                                 UserChapterProgressRepository chapterProgressRepository,
                                 UserQuizAttemptRepository quizAttemptRepository,
                                 IUtilisateurRepository utilisateurRepo, 
                                 UtilsService utilsService) {
        this.enrollmentRepository = enrollmentRepository;
        this.progressRepository = progressRepository;
        this.formationRepository = formationRepository;
        this.moduleRepository = moduleRepository;
        this.chapitreRepository = chapitreRepository;
        this.chapterProgressRepository = chapterProgressRepository;
        this.quizAttemptRepository = quizAttemptRepository;
        this.utilisateurRepo = utilisateurRepo;
        this.utilsService = utilsService;
    }

    @Override
    public UserFormationEnrollmentDTO enrollUser(String formationId) {
        
        UUID id = UUID.fromString(formationId);
        Utilisateur user = utilsService.getUser();
        // Vérifier si l'utilisateur est déjà inscrit
        Optional<UserFormationEnrollment> existing = enrollmentRepository.findByUserIdAndFormationId(user.getId(), id);
        if (existing.isPresent()) {
            UserFormationEnrollment saved = existing.get();
            return toEnrollmentDTO(saved);
        }

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

//    @Override
//    public ProgressEnrollement enrollUserProgression(String formationId) {
//        UUID id = UUID.fromString(formationId);
//        Utilisateur user = utilsService.getUser();
//        Optional<UserFormationEnrollment> existing = enrollmentRepository.findByUserIdAndFormationId(user.getId(), id);
//        if (existing.isEmpty()) {
//            throw new RuntimeException("Vous n'etes pas inscrit a cette formation");
//        }
//        Formation formation = formationRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));
//
//        if (!formation.getStatus().equals(Formation.FormationStatus.PUBLISHED)) {
//            throw new RuntimeException("Cette formation n'est pas encore publiée");
//        }
//        ProgressEnrollement.FormationsProgress formationsProgress = new ProgressEnrollement.FormationsProgress();
//        formationsProgress.setTitre(formation.getTitre());
//        formationsProgress.setId(formation.getId());
//        List<ProgressEnrollement.ModulesProgress> modules=new ArrayList<>();
//
//        formation.getModules().stream().forEach(module -> {
//
//        });
//        for(Modules modules : formation.getModules())
//
//
//        formationsProgress.setModules();
//        ProgressEnrollement progressEnrollement = new ProgressEnrollement();
//        progressEnrollement.setProgress();
//
//
//        return progressEnrollement;
//    }

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
    public List<UserFormationEnrollmentDTO> getUserEnrollments() {
        return enrollmentRepository.findByUserId(utilsService.getUser().getId()).stream()
                .filter(userFormationEnrollment -> userFormationEnrollment.getStatus()== UserFormationEnrollment.EnrollmentStatus.IN_PROGRESS || userFormationEnrollment.getStatus()== UserFormationEnrollment.EnrollmentStatus.COMPLETED)
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
    public UserFormationEnrollmentDTO getUserEnrollmentForFormation(String formationId) {
        
        UUID id = UUID.fromString(formationId);
        UserFormationEnrollment enrollment = enrollmentRepository.findByUserIdAndFormationId(utilsService.getUser().getId(), id)
                .orElseThrow(() -> new RuntimeException("Inscription non trouvée"));
        return toEnrollmentDTO(enrollment);
    }

    @Override
    public Map<String, String> getUserEnrollmentForFormationCode(String formationId) {
        UUID id = UUID.fromString(formationId);
        UserFormationEnrollment enrollment = enrollmentRepository.findByUserIdAndFormationId(utilsService.getUser().getId(), id)
                .orElseThrow(() -> new RuntimeException("Inscription non trouvée"));
        return Map.of("code", enrollment.getFormation().getCode());
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

    @Override
    @Transactional(readOnly = true)
    public DetailedProgressDTO getDetailedProgress(String enrollmentId) {
        UUID id = UUID.fromString(enrollmentId);
        
        // Récupérer l'inscription
        UserFormationEnrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscription non trouvée"));
        
        Formation formation = enrollment.getFormation();
        Utilisateur user = enrollment.getUser();
        
        // Construire le DTO de la formation
        DetailedProgressDTO.FormationProgressDetail formationDetail = new DetailedProgressDTO.FormationProgressDetail();
        formationDetail.setId(formation.getId());
        formationDetail.setTitre(formation.getTitre());
        formationDetail.setDescription(formation.getDescription());
        formationDetail.setProgressionPourcent(enrollment.getProgressionPourcent());
        
        List<DetailedProgressDTO.ModuleProgressDetail> moduleDetails = new ArrayList<>();
        
        // Récupérer tous les modules de la formation
        List<Modules> modules = moduleRepository.findByFormationIdOrderByOrdre(formation.getId());
        
        int completedModulesCount = 0;
        
        for (Modules module : modules) {
            DetailedProgressDTO.ModuleProgressDetail moduleDetail = new DetailedProgressDTO.ModuleProgressDetail();
            moduleDetail.setId(module.getId());
            moduleDetail.setTitre(module.getTitre());
            moduleDetail.setDescription(module.getDescription());
            moduleDetail.setOrdre(module.getOrdre());
            moduleDetail.setWithQuiz(module.getWithQuiz());
            
            // Récupérer tous les chapitres du module
            List<Chapitres> chapitres = chapitreRepository.findByModuleIdOrderByOrdre(module.getId());
            List<DetailedProgressDTO.ChapterProgressDetail> chapterDetails = new ArrayList<>();
            
            int completedChaptersCount = 0;
            
            for (Chapitres chapitre : chapitres) {
                DetailedProgressDTO.ChapterProgressDetail chapterDetail = new DetailedProgressDTO.ChapterProgressDetail();
                chapterDetail.setId(chapitre.getId());
                chapterDetail.setTitre(chapitre.getTitre());
                chapterDetail.setOrdre(chapitre.getOrdre());
                chapterDetail.setDureeEstimee(chapitre.getDureeEstimee());
                
                // Vérifier si le chapitre est complété
                Optional<UserChapterProgress> chapterProgress = chapterProgressRepository
                        .findByEnrollmentIdAndChapterId(id, chapitre.getId());
                
                boolean isChapterCompleted = chapterProgress.isPresent() && chapterProgress.get().getCompleted();
                chapterDetail.setCompleted(isChapterCompleted);
                
                if (isChapterCompleted) {
                    completedChaptersCount++;
                }
                
                chapterDetails.add(chapterDetail);
            }
            
            moduleDetail.setChapitres(chapterDetails);
            
            // Vérifier si le module a un quiz et s'il est passé
            boolean quizPassed = false;
            DetailedProgressDTO.QuizProgressDetail quizDetail = null;
            
            if (module.getWithQuiz() && module.getQuiz() != null) {
                Quiz quiz = module.getQuiz();
                quizDetail = new DetailedProgressDTO.QuizProgressDetail();
                quizDetail.setId(quiz.getId());
                quizDetail.setTitre(quiz.getTitre());
                quizDetail.setScoreMinimum(quiz.getScoreMinimum());
                
                // Récupérer la dernière tentative de quiz
                Optional<UserQuizAttempt> latestAttempt = quizAttemptRepository
                        .findLatestAttemptByUserIdAndQuizId(user.getId(), quiz.getId());
                
                if (latestAttempt.isPresent()) {
                    UserQuizAttempt attempt = latestAttempt.get();
                    quizPassed = attempt.getPassed() != null && attempt.getPassed();
                    quizDetail.setPassed(quizPassed);
                    quizDetail.setLastScore(attempt.getScoreObtenu());
                } else {
                    quizDetail.setPassed(false);
                    quizDetail.setLastScore(null);
                }
                
                moduleDetail.setQuiz(quizDetail);
            }
            
            // Calculer si le module est complété
            boolean allChaptersCompleted = (chapitres.size() > 0) && (completedChaptersCount == chapitres.size());
            boolean moduleCompleted;
            
            if (module.getWithQuiz()) {
                // Si le module a un quiz, il faut que tous les chapitres soient lus ET que le quiz soit passé
                moduleCompleted = allChaptersCompleted && quizPassed;
            } else {
                // Si pas de quiz, il suffit que tous les chapitres soient lus
                moduleCompleted = allChaptersCompleted;
            }
            
            moduleDetail.setCompleted(moduleCompleted);
            
            if (moduleCompleted) {
                completedModulesCount++;
            }
            
            moduleDetails.add(moduleDetail);
        }
        
        formationDetail.setModules(moduleDetails);
        
        // La formation est complétée si tous les modules sont complétés
        boolean formationCompleted = (modules.size() > 0) && (completedModulesCount == modules.size());
        formationDetail.setCompleted(formationCompleted);
        
        DetailedProgressDTO result = new DetailedProgressDTO();
        result.setEnrollmentId(id);
        result.setFormation(formationDetail);
        
        return result;
    }

    @Override
    public void markChapterAsCompleted(MarkChapterCompleteDTO dto) {
        // Vérifier que l'inscription existe
        UserFormationEnrollment enrollment = enrollmentRepository.findById(dto.getEnrollmentId())
                .orElseThrow(() -> new RuntimeException("Inscription non trouvée"));
        
        // Vérifier que le chapitre existe
        Chapitres chapter = chapitreRepository.findById(dto.getChapterId())
                .orElseThrow(() -> new RuntimeException("Chapitre non trouvé"));
        
        // Vérifier ou créer le progrès du chapitre
        UserChapterProgress chapterProgress = chapterProgressRepository
                .findByEnrollmentIdAndChapterId(dto.getEnrollmentId(), dto.getChapterId())
                .orElse(new UserChapterProgress());
        
        if (chapterProgress.getId() == null) {
            // Nouveau progrès
            chapterProgress.setEnrollment(enrollment);
            chapterProgress.setChapter(chapter);
            chapterProgress.setDateFirstView(new Date());
        }
        
        chapterProgress.setCompleted(true);
        chapterProgress.setDateCompletion(new Date());
        
        if (dto.getTimeSpentSeconds() != null) {
            chapterProgress.setTimeSpentSeconds(dto.getTimeSpentSeconds());
        }
        
        chapterProgressRepository.save(chapterProgress);
        
        // Mettre à jour le progrès du module si nécessaire
        updateModuleProgressBasedOnChapters(enrollment.getId().toString(), chapter.getModule().getId().toString());
    }
    
    /**
     * Met à jour le progrès d'un module en fonction de la complétion de ses chapitres
     */
    private void updateModuleProgressBasedOnChapters(String enrollmentId, String moduleId) {
        UUID enrollmentUuid = UUID.fromString(enrollmentId);
        UUID moduleUuid = UUID.fromString(moduleId);
        
        // Compter le nombre total de chapitres dans le module
        Long totalChapters = chapitreRepository.countByModuleId(moduleUuid);
        
        // Compter le nombre de chapitres complétés
        Long completedChapters = chapterProgressRepository
                .countCompletedChaptersByEnrollmentAndModule(enrollmentUuid, moduleUuid);
        
        // Récupérer le progrès du module ou en créer un nouveau
        UserProgress moduleProgress = progressRepository
                .findByEnrollmentIdAndModuleId(enrollmentUuid, moduleUuid)
                .orElseGet(() -> {
                    UserFormationEnrollment enrollment = enrollmentRepository.findById(enrollmentUuid)
                            .orElseThrow(() -> new RuntimeException("Inscription non trouvée"));
                    Modules module = moduleRepository.findById(moduleUuid)
                            .orElseThrow(() -> new RuntimeException("Module non trouvé"));
                    
                    UserProgress newProgress = new UserProgress();
                    newProgress.setEnrollment(enrollment);
                    newProgress.setModule(module);
                    newProgress.setStatus(UserProgress.ProgressStatus.NOT_STARTED);
                    newProgress.setProgressionPourcent(0.0);
                    newProgress.setDateCreation(new Date());
                    return progressRepository.save(newProgress);
                });
        
        // Calculer le pourcentage de progression basé sur les chapitres
        double progressPourcent = 0.0;
        if (totalChapters != null && totalChapters > 0) {
            progressPourcent = (completedChapters.doubleValue() / totalChapters.doubleValue()) * 100.0;
        }
        
        moduleProgress.setProgressionPourcent(progressPourcent);
        
        // Si tous les chapitres sont complétés
        if (totalChapters != null && completedChapters != null && totalChapters.equals(completedChapters)) {
            Modules module = moduleProgress.getModule();
            
            // Si le module n'a pas de quiz, le marquer comme complété
            if (!module.getWithQuiz()) {
                moduleProgress.setStatus(UserProgress.ProgressStatus.COMPLETED);
                moduleProgress.setDateCompletion(new Date());
            }
            // Si le module a un quiz, il faut vérifier si l'utilisateur l'a passé
            // (ce sera géré par le système de quiz existant)
        }
        
        progressRepository.save(moduleProgress);
        
        // Recalculer la progression globale de l'inscription
        calculateAndUpdateEnrollmentProgress(enrollmentId);
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
        if(enrollment.getProgresses() == null || enrollment.getProgresses().isEmpty()){
            dto.setProgresses(new ArrayList<>(0));
        }else{
            List<UserProgressDTO> userProgressDTOS = enrollment.getProgresses().stream().map(userProgress -> {
                return new UserProgressDTO(userProgress.getId(), userProgress.getEnrollment().getId(),
                        userProgress.getModule().getId(), userProgress.getModule().getTitre(),
                        userProgress.getStatus().name(), userProgress.getProgressionPourcent(),
                        userProgress.getDateDebut(), userProgress.getDateCompletion(),
                        userProgress.getScoreQuiz(), userProgress.getQuizPassed(),
                        userProgress.getDateCreation(), userProgress.getDateModification());
            }).collect(Collectors.toList());
            dto.setProgresses(userProgressDTOS);
        }

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
