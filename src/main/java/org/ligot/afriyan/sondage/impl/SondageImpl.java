package org.ligot.afriyan.sondage.impl;

import lombok.extern.slf4j.Slf4j;
import org.ligot.afriyan.elearning.entities.Formations;
import org.ligot.afriyan.elearning.repo.FormationsRepo;
import org.ligot.afriyan.sondage.dto.*;
import org.ligot.afriyan.sondage.entities.ModelResponse;
import org.ligot.afriyan.sondage.entities.QuestionResponse;
import org.ligot.afriyan.sondage.entities.Questions;
import org.ligot.afriyan.sondage.entities.Sondage;
import org.ligot.afriyan.sondage.enumerations.EtatSondage;
import org.ligot.afriyan.sondage.enumerations.TypeUserSondage;
import org.ligot.afriyan.sondage.mapper.CategorieEntitieMapper;
import org.ligot.afriyan.sondage.mapper.QuestionsMapper;
import org.ligot.afriyan.sondage.mapper.SondageMapper;
import org.ligot.afriyan.sondage.repo.CategorieEntitiesRepo;
import org.ligot.afriyan.sondage.repo.QuestionResponseRepo;
import org.ligot.afriyan.sondage.repo.SondageRepo;
import org.ligot.afriyan.sondage.service.AnswerService;
import org.ligot.afriyan.sondage.service.QuestionsService;
import org.ligot.afriyan.sondage.service.SchedulerService;
import org.ligot.afriyan.sondage.service.SondageService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class SondageImpl implements SondageService {

    private final SondageRepo repo;
    private final QuestionResponseRepo questionResponseRepo;
    private final SondageMapper mapper;
    private final QuestionsService questionsService;
    private final SchedulerService schedulerService;
    private final AnswerService answerService;
    private final CategorieEntitiesRepo categorieEntitiesRepo;
    private final CategorieEntitieMapper categorieEntitieMapper;
    private final QuestionsMapper questionsMapper;
    private final FormationsRepo formationsRepo;

    public SondageImpl(SondageRepo repo, QuestionResponseRepo questionResponseRepo, SondageMapper mapper, QuestionsService questionsService, SchedulerService schedulerService, AnswerService answerService, CategorieEntitiesRepo categorieEntitiesRepo, CategorieEntitieMapper categorieEntitieMapper, QuestionsMapper questionsMapper, FormationsRepo formationsRepo) {
        this.repo = repo;
        this.questionResponseRepo = questionResponseRepo;
        this.mapper = mapper;
        this.questionsService = questionsService;
        this.schedulerService = schedulerService;
        this.answerService = answerService;
        this.categorieEntitiesRepo = categorieEntitiesRepo;
        this.categorieEntitieMapper = categorieEntitieMapper;
        this.questionsMapper = questionsMapper;
        this.formationsRepo = formationsRepo;
    }


    @Override
    public SondageDTO save(SondageDTO sondageDTO) {
        Sondage sondage = mapper.toEntity(sondageDTO);
        sondage.setId(null);
        sondage.setCreateDate(LocalDateTime.now());
        sondage.setScheduler(null);
        sondage.setState(EtatSondage.CREATED);
        sondage.getQuestions().clear();
        sondage.getDomain().clear();
        Set<Questions> questions = new HashSet<>(0);
        sondageDTO.getQuestions().forEach(
                question -> {
                    try {
                        questions.add(questionsService.save(question));
                    } catch (Exception e) {
                    }
                }
        );
        sondage.setQuestions(questions);
        sondageDTO.getDomain().forEach(domain -> categorieEntitiesRepo.findByDomain(domain.getDomain()).ifPresent(sondage.getDomain()::add));
        Sondage sondageSave = repo.save(sondage);
        sondageDTO = mapper.toDTO(sondageSave);
        sondageDTO.getQuestions().clear();
        for (Questions question : sondageSave.getQuestions()){
            QuestionsDTO questionsDTO = questionsMapper.toDTO(question);
            questionsDTO.getModelResponses().clear();
            Set<ModelResponseDTO> modelResponseDTOS = new HashSet<>(0);
            for (ModelResponse modelResponse : question.getModelResponses()){
                modelResponseDTOS.add(new ModelResponseDTO(modelResponse.getId(), modelResponse.getValue()));
            }
            questionsDTO.setModelResponses(modelResponseDTOS);
            sondageDTO.getQuestions().add(questionsDTO);
        }
        return sondageDTO;
    }

    @Override
    public void schedule(Long idSondage, SchedulerDTO schedulerDTO) throws Exception {
        Sondage sondage = repo.findById(idSondage).orElseThrow(()->new Exception("Sondage not found"));
        if(LocalDateTime.now().isAfter(schedulerDTO.getStartDate()))
            throw new Exception("Date is not valid");
        sondage.setScheduler(schedulerService.save(schedulerDTO));
        sondage.setState(EtatSondage.SCHEDULED);
        repo.save(sondage);
    }

    @Override
    public void archive(Long idSondage) throws Exception {
        Sondage sondage = repo.findById(idSondage).orElseThrow(()->new Exception("Sondage not found"));
        sondage.setState(EtatSondage.ARCHIVE);
        if(LocalDateTime.now().isBefore(sondage.getScheduler().getEndDate()))
            throw new Exception("Date is not valid");
        repo.save(sondage);
    }

    @Override
    public void assignResponseToQuestion(QuestionResponseDTO body) throws Exception {
        Sondage sondage = repo.findById(body.getIdSondage()).orElseThrow(()->new Exception("Sondage not found"));
        if(sondage.getTypeUser()!=TypeUserSondage.FORMATION)
            throw new Exception("Sondage n'est pas pour les quizz");
        Formations formations = formationsRepo.findById(body.getIdFormation()).orElseThrow(()->new RuntimeException("Formation nont found"));
        Map<String, List> map = new HashMap<>(0);
        sondage.getQuestions().forEach(questions -> {
            body.getBody().forEach(questionsResponseBodyDTO -> {
                if(questionsResponseBodyDTO.getQuestion()== questions.getId()){
                    questions.getModelResponses().forEach(modelResponse -> {
                        if(modelResponse.getId()==questionsResponseBodyDTO.getResponse()){
                            questionResponseRepo.save(
                                    new QuestionResponse(
                                            null,
                                            questions.getId(),
                                            body.getIdSondage(),
                                            modelResponse.getId()
                                    )
                            );
                        }
                    });
                }
            });
        });
        formations.setConfigure(Boolean.TRUE.booleanValue());
        formationsRepo.save(formations);
    }

    @Override
    public void update(Long id, SondageDTO sondageDTO) throws Exception {
        Sondage sondage = repo.findById(id).orElseThrow(()->new Exception("Sondage not found"));
        if(!Objects.equals(sondage.getId(), sondageDTO.getId()))
            throw new Exception("Data not match");
        mapper.update(sondageDTO, sondage);
        repo.save(sondage);
    }

    @Override
    public SondageDTO findById(Long id) throws Exception {

        Sondage sondage = repo.findById(id).orElseThrow(()->new Exception("Sondage not found"));
        SondageDTO sondageDTO = mapper.toDTO(sondage);
        sondageDTO.getQuestions().clear();
        for (Questions question : sondage.getQuestions()){
            QuestionsDTO questionsDTO = questionsMapper.toDTO(question);
            questionsDTO.getModelResponses().clear();
            Set<ModelResponseDTO> modelResponseDTOS = new HashSet<>(0);
            for (ModelResponse modelResponse : question.getModelResponses()){
                modelResponseDTOS.add(new ModelResponseDTO(modelResponse.getId(), modelResponse.getValue()));
            }
            questionsDTO.setModelResponses(modelResponseDTOS);
            sondageDTO.getQuestions().add(questionsDTO);
        }
        return sondageDTO;
    }

    @Override
    public List<SondageDTO> findAllSondage() {
        List<Sondage> sondageList = repo.findAll();
        List<SondageDTO> sondageDTOS = new ArrayList<>(0);
        for (Sondage sondage : sondageList){
            SondageDTO sondageDTO = mapper.toDTO(sondage);
            sondageDTO.getQuestions().clear();
            for (Questions question : sondage.getQuestions()){
                QuestionsDTO questionsDTO = questionsMapper.toDTO(question);
                questionsDTO.getModelResponses().clear();
                Set<ModelResponseDTO> modelResponseDTOS = new HashSet<>(0);
                for (ModelResponse modelResponse : question.getModelResponses()){
                    modelResponseDTOS.add(new ModelResponseDTO(modelResponse.getId(), modelResponse.getValue()));
                }
                questionsDTO.setModelResponses(modelResponseDTOS);
                sondageDTO.getQuestions().add(questionsDTO);
            }
            sondageDTOS.add(sondageDTO);
        }
        return sondageDTOS;
    }

    @Override
    public List<SondageDTO> findAllSondageDTO(String etat) throws Exception {
        EtatSondage state = EtatSondage.valueOf(etat.toUpperCase());
        List<Sondage> sondageList = repo.findSondagesByState(state);
        List<SondageDTO> sondageDTOS = new ArrayList<>(0);
        for (Sondage sondage : sondageList){
            SondageDTO sondageDTO = mapper.toDTO(sondage);
            sondageDTO.getQuestions().clear();
            for (Questions question : sondage.getQuestions()){
                QuestionsDTO questionsDTO = questionsMapper.toDTO(question);
                questionsDTO.getModelResponses().clear();
                Set<ModelResponseDTO> modelResponseDTOS = new HashSet<>(0);
                for (ModelResponse modelResponse : question.getModelResponses()){
                    modelResponseDTOS.add(new ModelResponseDTO(modelResponse.getId(), modelResponse.getValue()));
                }
                questionsDTO.setModelResponses(modelResponseDTOS);
                sondageDTO.getQuestions().add(questionsDTO);
            }
            sondageDTOS.add(sondageDTO);
        }
        return sondageDTOS;
    }

    @Override
    public List<SondageDTO> findAllSondageByTypeUserAndState(TypeUserSondage typeUserSondage, EtatSondage state) throws Exception {
        List<Sondage> sondageList = repo.findSondagesByTypeUserAndState(typeUserSondage, state);
        List<SondageDTO> sondageDTOS = new ArrayList<>(0);
        for (Sondage sondage : sondageList){
            SondageDTO sondageDTO = mapper.toDTO(sondage);
            sondageDTO.getQuestions().clear();
            for (Questions question : sondage.getQuestions()){
                QuestionsDTO questionsDTO = questionsMapper.toDTO(question);
                questionsDTO.getModelResponses().clear();
                Set<ModelResponseDTO> modelResponseDTOS = new HashSet<>(0);
                for (ModelResponse modelResponse : question.getModelResponses()){
                    modelResponseDTOS.add(new ModelResponseDTO(modelResponse.getId(), modelResponse.getValue()));
                }
                questionsDTO.setModelResponses(modelResponseDTOS);
                sondageDTO.getQuestions().add(questionsDTO);
            }
            sondageDTOS.add(sondageDTO);
        }
        return sondageDTOS;
    }

    @Override
    public List<SondageDTO> findAllSondageFormationAvaillable() throws Exception {
        List<SondageDTO> sondageDTOS = new ArrayList<>(0);
        List<Long> quizzes = formationsRepo.findFormationsByQuizzIsNotNull().stream().map(formations -> formations.getQuizz().getId()).toList();
        repo.findSondagesByTypeUserAndState(TypeUserSondage.FORMATION, EtatSondage.ACTIVE)
                .stream()
                .map(mapper::toDTO)
                .toList().forEach(sondageDTO -> {
                    if(!quizzes.contains(sondageDTO.getId()))
                        sondageDTOS.add(sondageDTO);
                });
        return sondageDTOS;
    }

    @Override
    public List<Sondage> findAllSondage(EtatSondage state) {
        return repo.findSondagesByState(state);
    }

    @Override
    public void doSondage(Set<AnswerDTO> answerDTOSet) throws Exception {
        Sondage sondage = repo.findById(answerDTOSet.stream().toList().get(0).getSondageId()).orElseThrow(()->new Exception("Sondage not found"));
        if(sondage.getState() != EtatSondage.ACTIVE)
            throw new Exception("You can't pass this sondage because he status is :"+sondage.getState());
        answerDTOSet.forEach(
            answerDTO -> {
              try {
                Questions questions = questionsService.findByIdEntity(answerDTO.getQuestionId());
                for (ModelResponse modelResponses : questions.getModelResponses()) {
                  if (Objects.equals(modelResponses.getId(), answerDTO.getValues().get(0).getId())) {
                      answerService.save(answerDTO);
                  }
                }
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
        );
    }

    @Override
    public void setStatus(Long id, String etatSondage) throws Exception {
        Sondage sondage = repo.findById(id).orElse(null);
        if(sondage!=null){
           sondage.setState(EtatSondage.valueOf(etatSondage));
           repo.save(sondage);
        }
    }

    @Override
    public Map<String, String> sondageStat(Long id) throws Exception {
        return null;
    }

    @Override
    public Map<String, String> sondageStat() throws Exception {
        return null;
    }

    @Override
    public void updateQuestion(Long idSondage, Long idQuestion, QuestionsDTO questionsDTO) throws Exception {
        List<EtatSondage> states = new ArrayList<>(0);
        states.add(EtatSondage.ACTIVE);
        states.add(EtatSondage.ARCHIVE);
        Sondage sondage = verifieStatus(idSondage, states);
        if(!sondage.getQuestions().stream().map(Questions::getId).toList().contains(idQuestion))
            throw new Exception("Incoherent Data");
        questionsService.update(idQuestion, questionsDTO);
    }

    @Override
    public List<CategorieEntitiesDTO> findCategoriesDTO() {
        return categorieEntitiesRepo.findAll().stream().map(categorieEntitieMapper::toDTO).toList();
    }

    private Sondage verifieStatus(Long id, List<EtatSondage> states) throws Exception {
        Sondage sondage = repo.findById(id).orElseThrow(()->new Exception("Sondage not found"));
        for (EtatSondage state: states)
            if(sondage.getState() == state)
                throw new Exception("This sondage status is :"+sondage.getState());
        return sondage;
    }
}
