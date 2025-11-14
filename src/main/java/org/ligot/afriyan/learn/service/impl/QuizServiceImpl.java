package org.ligot.afriyan.learn.service.impl;

import org.ligot.afriyan.implement.UtilsService;
import org.ligot.afriyan.learn.dto.*;
import org.ligot.afriyan.learn.entities.*;
import org.ligot.afriyan.learn.repository.*;
import org.ligot.afriyan.learn.service.QuizService;
import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.repository.IUtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuestionOptionRepository optionRepository;
    private final UserQuizAttemptRepository attemptRepository;
    private final UserQuizAnswerRepository answerRepository;
    private final UserFormationEnrollmentRepository enrollmentRepository;
    private final UserProgressRepository progressRepository;
    private final ModuleRepository moduleRepository;
    private final FormationRepository formationRepository;
    private final IUtilisateurRepository utilisateurRepo;
    private final UtilsService utilsService;

    public QuizServiceImpl(QuizRepository quizRepository, QuestionRepository questionRepository, QuestionOptionRepository optionRepository, UserQuizAttemptRepository attemptRepository, UserQuizAnswerRepository answerRepository, UserFormationEnrollmentRepository enrollmentRepository, UserProgressRepository progressRepository, ModuleRepository moduleRepository, FormationRepository formationRepository, IUtilisateurRepository utilisateurRepo, UtilsService utilsService) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
        this.attemptRepository = attemptRepository;
        this.answerRepository = answerRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.progressRepository = progressRepository;
        this.moduleRepository = moduleRepository;
        this.formationRepository = formationRepository;
        this.utilisateurRepo = utilisateurRepo;
        this.utilsService = utilsService;
    }

    @Override
    public QuizDTO createQuiz(QuizCreateDTO dto) {
        Quiz quiz = new Quiz();
        quiz.setTitre(dto.getTitre());
        quiz.setDescription(dto.getDescription());
        quiz.setType(Quiz.QuizType.valueOf(dto.getType()));
        quiz.setScoreMinimum(dto.getScoreMinimum() != null ? dto.getScoreMinimum() : 50);
        quiz.setDureeLimite(dto.getDureeLimite());
        quiz.setNombreTentativesMax(dto.getNombreTentativesMax());

        if (dto.getModuleId() != null) {
            Modules module = moduleRepository.findById(dto.getModuleId())
                    .orElseThrow(() -> new RuntimeException("Module non trouvé"));
            quiz.setModule(module);
        }

        if (dto.getFormationId() != null) {
            Formation formation = formationRepository.findById(dto.getFormationId())
                    .orElseThrow(() -> new RuntimeException("Formation non trouvée"));
            quiz.setFormation(formation);
        }

        Quiz saved = quizRepository.save(quiz);



        if (dto.getModuleId() != null) {
            Modules module = moduleRepository.findById(dto.getModuleId())
                    .orElseThrow(() -> new RuntimeException("Module non trouvé"));
            module.setQuiz(saved);
            module.setWithQuiz(true);
        }

        if (dto.getFormationId() != null) {
            Formation formation = formationRepository.findById(dto.getFormationId())
                    .orElseThrow(() -> new RuntimeException("Formation non trouvée"));
            formation.setQuizFinal(saved);
            formation.setWithFinalQuiz(true);
        }

        return toQuizDTO(saved);
    }

    @Override
    public QuizDTO updateQuiz(String id, QuizCreateDTO dto) {
        Quiz quiz = quizRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Quiz non trouvé"));

        quiz.setTitre(dto.getTitre());
        quiz.setDescription(dto.getDescription());
        quiz.setScoreMinimum(dto.getScoreMinimum() != null ? dto.getScoreMinimum() : 50);
        quiz.setDureeLimite(dto.getDureeLimite());
        quiz.setNombreTentativesMax(dto.getNombreTentativesMax());

        Quiz saved = quizRepository.save(quiz);
        return toQuizDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public QuizDTO getQuizById(String id) {
        Quiz quiz = quizRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Quiz non trouvé"));
        return toQuizDTO(quiz);
    }

    @Override
    @Transactional(readOnly = true)
    public QuizDTO getQuizByModuleId(String moduleId) {
        Quiz quiz = quizRepository.findByModuleId(UUID.fromString(moduleId))
                .orElseThrow(() -> new RuntimeException("Quiz non trouvé pour ce module"));
        return toQuizDTO(quiz);
    }

    @Override
    @Transactional(readOnly = true)
    public QuizDTO getQuizByFormationId(String formationId) {
        Quiz quiz = quizRepository.findByFormationId(UUID.fromString(formationId))
                .orElseThrow(() -> new RuntimeException("Quiz non trouvé pour cette formation"));
        return toQuizDTO(quiz);
    }

    @Override
    public void deleteQuiz(String id) {
        quizRepository.deleteById(UUID.fromString(id));
    }

    @Override
    public QuestionDTO createQuestion(QuestionCreateDTO dto) {
        Quiz quiz = quizRepository.findById(dto.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz non trouvé"));

        Question question = new Question();
        question.setIntitule(dto.getIntitule());
        question.setExplication(dto.getExplication());
        question.setPoints(dto.getPoints() != null ? dto.getPoints() : 1);
        question.setOrdre(dto.getOrdre());
        question.setImageUrl(dto.getImageUrl());
        question.setQuiz(quiz);

        Question savedQuestion = questionRepository.save(question);

        // Créer les options
        if (dto.getOptions() != null && !dto.getOptions().isEmpty()) {
            for (QuestionOptionCreateDTO optionDto : dto.getOptions()) {
                QuestionOption option = new QuestionOption();
                option.setTexte(optionDto.getTexte());
                option.setIsCorrect(optionDto.getIsCorrect());
                option.setOrdre(optionDto.getOrdre());
                option.setQuestion(savedQuestion);
                System.err.println("Saving optionDto: " + optionDto.getTexte());
                System.err.println("Saving optionDto is true: " + (optionDto.getIsCorrect() ? "true" : "false"));
                System.err.println("Saving option: " + option.getTexte());
                System.err.println("Saving option is true: " + (option.getIsCorrect() ? "true" : "false"));
                System.err.println("***********************************************************************");
                optionRepository.save(option);
            }
        }

        return toQuestionDTO(savedQuestion);
    }

    @Override
    public QuestionDTO updateQuestion(String id, QuestionCreateDTO dto) {
        Question question = questionRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Question non trouvée"));

        question.setIntitule(dto.getIntitule());
        question.setExplication(dto.getExplication());
        question.setPoints(dto.getPoints() != null ? dto.getPoints() : 1);
        question.setOrdre(dto.getOrdre());
        question.setImageUrl(dto.getImageUrl());

        Question saved = questionRepository.save(question);
        return toQuestionDTO(saved);
    }

    @Override
    public void deleteQuestion(String id) {
        questionRepository.deleteById(UUID.fromString(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionDTO> getQuestionsByQuizId(String quizId) {
        return questionRepository.findByQuizIdOrderByOrdre(UUID.fromString(quizId)).stream()
                .map(this::toQuestionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserQuizAttemptDTO startQuizAttempt(String quizId, String enrollmentId) {
        Long userId = utilsService.getUser().getId();
        if (!canUserAttemptQuiz(quizId)) {
            throw new RuntimeException("Nombre maximum de tentatives atteint");
        }

        Utilisateur user = utilisateurRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        Quiz quiz = quizRepository.findById(UUID.fromString(quizId))
                .orElseThrow(() -> new RuntimeException("Quiz non trouvé"));

        UserFormationEnrollment enrollment = null;
        if (enrollmentId != null) {
            enrollment = enrollmentRepository.findById(UUID.fromString(enrollmentId))
                    .orElseThrow(() -> new RuntimeException("Inscription non trouvée"));
        }

        // Compter le nombre de tentatives existantes
        Long attemptCount = attemptRepository.countAttemptsByUserIdAndQuizId(userId, UUID.fromString(quizId));
        
        UserQuizAttempt attempt = new UserQuizAttempt();
        attempt.setUser(user);
        attempt.setQuiz(quiz);
        attempt.setEnrollment(enrollment);
        attempt.setNumeroTentative((attemptCount != null ? attemptCount.intValue() : 0) + 1);
        attempt.setCompleted(false);
        attempt.setPassed(false);
        attempt.setDateDebut(new Date());

        UserQuizAttempt saved = attemptRepository.save(attempt);
        return toAttemptDTO(saved);
    }

    @Override
    public UserQuizAttemptDTO submitQuizAttempt(SubmitQuizDTO dto) {
        UserQuizAttempt attempt = attemptRepository.findById(dto.getAttemptId())
                .orElseThrow(() -> new RuntimeException("Tentative non trouvée"));

        if (attempt.getCompleted()) {
            throw new RuntimeException("Cette tentative a déjà été soumise");
        }

        Quiz quiz = attempt.getQuiz();
        int totalPoints = 0;
        double obtainedPoints = 0;
        Map<Question, List<QuestionOption>> currentQuestion = new HashMap<>();


        // Traiter chaque réponse
        for (QuizAnswerDTO answerDto : dto.getAnswers()) {
            Question question = questionRepository.findById(answerDto.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question non trouvée"));
            if(!currentQuestion.containsKey(question)) {
                currentQuestion.put(question, new ArrayList<>());
                totalPoints += question.getPoints();
            }

            QuestionOption selectedOption = null;
            if (answerDto.getSelectedOptionId() != null) {
                selectedOption = optionRepository.findById(answerDto.getSelectedOptionId())
                        .orElse(null);
            }

            UserQuizAnswer answer = new UserQuizAnswer();
            answer.setAttempt(attempt);
            answer.setQuestion(question);
            answer.setSelectedOption(selectedOption);
            
            boolean isCorrect = selectedOption != null && selectedOption.getIsCorrect();
            answer.setIsCorrect(isCorrect);
            answer.setPointsObtenus(isCorrect ? question.getPoints() : 0);
            answer.setDateReponse(new Date());

            if (isCorrect) {
                List<QuestionOption> response = (List) currentQuestion.get(question);
                response.add(selectedOption);
                currentQuestion.put(question, response);
            }

            answerRepository.save(answer);
        }

        for (Map.Entry<Question, List<QuestionOption>> entry : currentQuestion.entrySet()) {
            long totalTrue = entry.getKey().getOptions().stream().filter(questionOption -> questionOption.getIsCorrect()==true).count();
            long totalResponse = entry.getValue().size();
            obtainedPoints += (totalResponse / totalTrue) * entry.getKey().getPoints();
        }

        // Calculer le score
        double scorePercent = totalPoints > 0 ? (obtainedPoints * 100.0 / totalPoints) : 0.0;
        
        attempt.setPointsObtenus((int) Math.floor(obtainedPoints));
        attempt.setPointsTotaux(totalPoints);
        attempt.setScoreObtenu(scorePercent);
        attempt.setCompleted(true);
        attempt.setPassed(scorePercent >= quiz.getScoreMinimum());
        attempt.setDateFin(new Date());
        
        // Calculer la durée
        long durationMs = attempt.getDateFin().getTime() - attempt.getDateDebut().getTime();
        attempt.setDureeSecondes((int) (durationMs / 1000));

        UserQuizAttempt saved = attemptRepository.save(attempt);

        // Si c'est un quiz de module, mettre à jour la progression
        if (quiz.getModule() != null && attempt.getEnrollment() != null) {
            updateModuleProgressFromQuiz(attempt.getEnrollment().getId().toString(),
                                        quiz.getModule().getId().toString(),
                                        scorePercent, 
                                        attempt.getPassed());
        }

        // Si c'est le quiz final, mettre à jour le statut de l'inscription
        if (quiz.getFormation() != null && attempt.getEnrollment() != null) {
            updateEnrollmentFromFinalQuiz(attempt.getEnrollment().getId().toString(),
                                         scorePercent, 
                                         attempt.getPassed());
        }

        return toAttemptDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserQuizAttemptDTO> getUserQuizAttempts(String quizId) {
        return attemptRepository.findByUserIdAndQuizIdOrderByNumeroTentativeDesc(utilsService.getUser().getId(), UUID.fromString(quizId))
                .stream()
                .map(this::toAttemptDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserQuizAttemptDTO getAttemptById(String attemptId) {
        UserQuizAttempt attempt = attemptRepository.findById(UUID.fromString(attemptId))
                .orElseThrow(() -> new RuntimeException("Tentative non trouvée"));
        return toAttemptDTO(attempt);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canUserAttemptQuiz(String quizId) {
        Long userId = utilsService.getUser().getId();
        Quiz quiz = quizRepository.findById(UUID.fromString(quizId))
                .orElseThrow(() -> new RuntimeException("Quiz non trouvé"));

        if (quiz.getNombreTentativesMax() == null) {
            return true; // Tentatives illimitées
        }

        Long attemptCount = attemptRepository.countAttemptsByUserIdAndQuizId(userId, UUID.fromString(quizId));
        return attemptCount == null || attemptCount < quiz.getNombreTentativesMax();
    }

    private void updateModuleProgressFromQuiz(String enrollmentId, String moduleId,
                                             Double score, Boolean passed) {
        UserProgress progress = progressRepository.findByEnrollmentIdAndModuleId(UUID.fromString(enrollmentId), UUID.fromString(moduleId))
                .orElseThrow(() -> new RuntimeException("Progression non trouvée"));

        progress.setScoreQuiz(score);
        progress.setQuizPassed(passed);

        if (passed) {
            progress.setStatus(UserProgress.ProgressStatus.COMPLETED);
            progress.setProgressionPourcent(100.0);
            progress.setDateCompletion(new Date());
        }

        progressRepository.save(progress);
    }

    private void updateEnrollmentFromFinalQuiz(String enrollmentId, Double score, Boolean passed) {
        UserFormationEnrollment enrollment = enrollmentRepository.findById(UUID.fromString(enrollmentId))
                .orElseThrow(() -> new RuntimeException("Inscription non trouvée"));

        enrollment.setScoreFinal(score);
        
        if (passed) {
            enrollment.setStatus(UserFormationEnrollment.EnrollmentStatus.COMPLETED);
            enrollment.setDateFin(new Date());
        } else {
            enrollment.setStatus(UserFormationEnrollment.EnrollmentStatus.FAILED);
        }

        enrollmentRepository.save(enrollment);
    }

    private QuizDTO toQuizDTO(Quiz quiz) {
        QuizDTO dto = new QuizDTO();
        dto.setId(quiz.getId());
        dto.setTitre(quiz.getTitre());
        dto.setDescription(quiz.getDescription());
        dto.setType(quiz.getType().name());
        dto.setScoreMinimum(quiz.getScoreMinimum());
        dto.setDureeLimite(quiz.getDureeLimite());
        dto.setNombreTentativesMax(quiz.getNombreTentativesMax());
        dto.setDateCreation(quiz.getDateCreation());
        dto.setDateModification(quiz.getDateModification());
        
        if (quiz.getModule() != null) {
            dto.setModuleId(quiz.getModule().getId());
        }
        
        if (quiz.getFormation() != null) {
            dto.setFormationId(quiz.getFormation().getId());
        }

        Long questionCount = questionRepository.countByQuizId(quiz.getId());
        dto.setNombreQuestions(questionCount != null ? questionCount.intValue() : 0);

        Integer totalPoints = questionRepository.getTotalPointsByQuizId(quiz.getId());
        dto.setPointsTotaux(totalPoints != null ? totalPoints : 0);

        List<QuestionDTO> questionDTOS = questionRepository.findByQuizIdOrderByOrdre(quiz.getId())
                .stream().map(this::toQuestionDTO).toList();

        dto.setQuestions(questionDTOS);
        return dto;
    }

    private QuestionDTO toQuestionDTO(Question question) {
        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setIntitule(question.getIntitule());
        dto.setExplication(question.getExplication());
        dto.setPoints(question.getPoints());
        dto.setOrdre(question.getOrdre());
        dto.setImageUrl(question.getImageUrl());
        dto.setQuizId(question.getQuiz().getId());
        dto.setDateCreation(question.getDateCreation());
        dto.setDateModification(question.getDateModification());

        List<QuestionOption> options = optionRepository.findByQuestionIdOrderByOrdre(question.getId());
        dto.setOptions(options.stream().map(this::toOptionDTO).collect(Collectors.toList()));

        return dto;
    }

    private QuestionOptionDTO toOptionDTO(QuestionOption option) {
        QuestionOptionDTO dto = new QuestionOptionDTO();
        dto.setId(option.getId());
        dto.setTexte(option.getTexte());
        dto.setIsCorrect(option.getIsCorrect());
        dto.setOrdre(option.getOrdre());
        dto.setQuestionId(option.getQuestion().getId());
        return dto;
    }

    private UserQuizAttemptDTO toAttemptDTO(UserQuizAttempt attempt) {
        UserQuizAttemptDTO dto = new UserQuizAttemptDTO();
        dto.setId(attempt.getId());
        dto.setUserId(attempt.getUser().getId());
        dto.setQuizId(attempt.getQuiz().getId());
        dto.setQuizTitre(attempt.getQuiz().getTitre());
        
        if (attempt.getEnrollment() != null) {
            dto.setEnrollmentId(attempt.getEnrollment().getId());
        }
        
        dto.setNumeroTentative(attempt.getNumeroTentative());
        dto.setScoreObtenu(attempt.getScoreObtenu());
        dto.setPointsObtenus(attempt.getPointsObtenus());
        dto.setPointsTotaux(attempt.getPointsTotaux());
        dto.setPassed(attempt.getPassed());
        dto.setCompleted(attempt.getCompleted());
        dto.setDateDebut(attempt.getDateDebut());
        dto.setDateFin(attempt.getDateFin());
        dto.setDureeSecondes(attempt.getDureeSecondes());

        return dto;
    }
}
