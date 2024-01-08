package org.ligot.afriyan.sondage.impl;

import lombok.extern.slf4j.Slf4j;
import org.ligot.afriyan.sondage.dto.*;
import org.ligot.afriyan.sondage.entities.Questions;
import org.ligot.afriyan.sondage.entities.Sondage;
import org.ligot.afriyan.sondage.enumerations.EtatSondage;
import org.ligot.afriyan.sondage.mapper.CategorieEntitieMapper;
import org.ligot.afriyan.sondage.mapper.SondageMapper;
import org.ligot.afriyan.sondage.repo.CategorieEntitiesRepo;
import org.ligot.afriyan.sondage.repo.SondageRepo;
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
    private final SondageMapper mapper;
    private final QuestionsService questionsService;
    private final SchedulerService schedulerService;
    private final CategorieEntitiesRepo categorieEntitiesRepo;
    private final CategorieEntitieMapper categorieEntitieMapper;

    public SondageImpl(SondageRepo repo, SondageMapper mapper, QuestionsService questionsService, SchedulerService schedulerService, CategorieEntitiesRepo categorieEntitiesRepo, CategorieEntitieMapper categorieEntitieMapper) {
        this.repo = repo;
        this.mapper = mapper;
        this.questionsService = questionsService;
        this.schedulerService = schedulerService;
        this.categorieEntitiesRepo = categorieEntitiesRepo;
        this.categorieEntitieMapper = categorieEntitieMapper;
    }

    @Override
    public void save(SondageDTO sondageDTO) {
        Sondage sondage = mapper.toEntity(sondageDTO);
        sondage.setId(null);
        sondage.setCreateDate(LocalDateTime.now());
        sondage.setScheduler(null);
        sondage.setState(EtatSondage.CREATED);
        sondage.getQuestions().clear();
        sondage.getDomain().clear();
        sondageDTO.getQuestions().forEach(
                System.err::println
                /*
                questions -> {
                    try {
                        sondage.getQuestions().add(questionsService.save(questions));
                    } catch (Exception e) {
                    }
                }
        */);
        sondageDTO.getDomain().forEach(domain -> categorieEntitiesRepo.findById(domain.getId()).ifPresent(sondage.getDomain()::add));
        repo.save(sondage);
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
    public void update(Long id, SondageDTO sondageDTO) throws Exception {
        Sondage sondage = repo.findById(id).orElseThrow(()->new Exception("Sondage not found"));
        if(!Objects.equals(sondage.getId(), sondageDTO.getId()))
            throw new Exception("Data not match");
        mapper.update(sondageDTO, sondage);
        repo.save(sondage);
    }

    @Override
    public SondageDTO findById(Long id) throws Exception {
        return mapper.toDTO(repo.findById(id).orElseThrow(()->new Exception("Sondage not found")));
    }

    @Override
    public List<SondageDTO> findAllSondage() {
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public List<SondageDTO> findAllSondageDTO(String etat) throws Exception {
        EtatSondage state = EtatSondage.valueOf(etat.toUpperCase());
        return repo.findSondagesByState(state).stream().map(mapper::toDTO).toList();
    }

    @Override
    public List<Sondage> findAllSondage(EtatSondage state) {
        return repo.findSondagesByState(state);
    }

    @Override
    public void doSondage(Long sondageId, Set<QuestionsDTO> questionsList) throws Exception {
        Sondage sondage = repo.findById(sondageId).orElseThrow(()->new Exception("Sondage not found"));
        if(sondage.getState() != EtatSondage.ACTIVE)
            throw new Exception("You can't pass this sondage because he status is :"+sondage.getState());
        questionsList.forEach(
                questions ->{
                    try {
                        AnswerDTO answerDTO = (AnswerDTO) questions.getValues().toArray()[0];
                        questionsService.addAnswer(questions.getId(), answerDTO);
                    } catch (Exception e) {
                    }
                }
        );
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
