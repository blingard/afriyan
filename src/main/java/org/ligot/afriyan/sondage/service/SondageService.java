package org.ligot.afriyan.sondage.service;

import org.ligot.afriyan.sondage.dto.CategorieEntitiesDTO;
import org.ligot.afriyan.sondage.dto.QuestionsDTO;
import org.ligot.afriyan.sondage.dto.SchedulerDTO;
import org.ligot.afriyan.sondage.dto.SondageDTO;
import org.ligot.afriyan.sondage.entities.Sondage;
import org.ligot.afriyan.sondage.enumerations.EtatSondage;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SondageService {
    void save(SondageDTO sondageDTO);
    void schedule(Long idSondage, SchedulerDTO schedulerDTO) throws Exception;
    void archive(Long idSondage) throws Exception;
    void update(Long id, SondageDTO sondageDTO) throws Exception;
    SondageDTO findById(Long id) throws Exception;
    List<SondageDTO> findAllSondage();
    List<SondageDTO> findAllSondageDTO(String etat) throws Exception ;
    List<Sondage> findAllSondage(EtatSondage state);
    void doSondage(Long sondageId, Set<QuestionsDTO> questionsList) throws Exception;
    Map<String, String> sondageStat(Long id) throws Exception;
    Map<String, String> sondageStat() throws Exception;
    void updateQuestion(Long idSondage, Long idQuestion, QuestionsDTO questionsDTO) throws Exception;

    List<CategorieEntitiesDTO> findCategoriesDTO();
}
