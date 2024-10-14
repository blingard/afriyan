package org.ligot.afriyan.sondage.service;

import org.ligot.afriyan.sondage.dto.*;
import org.ligot.afriyan.sondage.entities.Sondage;
import org.ligot.afriyan.sondage.enumerations.EtatSondage;
import org.ligot.afriyan.sondage.enumerations.TypeUserSondage;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SondageService {
    SondageDTO save(SondageDTO sondageDTO);
    void schedule(Long idSondage, SchedulerDTO schedulerDTO) throws Exception;
    void archive(Long idSondage) throws Exception;
    void assignResponseToQuestion(QuestionResponseDTO body) throws Exception;
    void update(Long id, SondageDTO sondageDTO) throws Exception;
    SondageDTO findById(Long id) throws Exception;
    List<SondageDTO> findAllSondage();
    List<SondageDTO> findAllSondageDTO(String etat) throws Exception ;
    List<SondageDTO> findAllSondageByTypeUserAndState(TypeUserSondage typeUserSondage, EtatSondage state) throws Exception ;
    List<SondageDTO> findAllSondageFormationAvaillable() throws Exception ;
    List<Sondage> findAllSondage(EtatSondage state);
    void doSondage(Set<AnswerDTO> answerDTOSet) throws Exception;
    void setStatus(Long id, String etatSondage) throws Exception;
    Map<String, String> sondageStat(Long id) throws Exception;
    Map<String, String> sondageStat() throws Exception;
    void updateQuestion(Long idSondage, Long idQuestion, QuestionsDTO questionsDTO) throws Exception;

    List<CategorieEntitiesDTO> findCategoriesDTO();
}
