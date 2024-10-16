package org.ligot.afriyan.elearning.service;

import org.ligot.afriyan.elearning.dto.HistoriquesLearningDTO;

import java.util.List;
import java.util.Map;

public interface HistoriquesLearningService {
    void save(HistoriquesLearningDTO historiquesLearningDTO)throws Exception;
    void passTest(Long id)throws Exception;
    List<HistoriquesLearningDTO> getAllUserHistoricOfFormation(Long userId, Long formationId)throws Exception;

}
