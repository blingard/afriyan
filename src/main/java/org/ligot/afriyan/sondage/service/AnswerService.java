package org.ligot.afriyan.sondage.service;

import org.ligot.afriyan.sondage.dto.AnswerDTO;
import org.ligot.afriyan.sondage.entities.Answer;

public interface AnswerService {
    Answer save(AnswerDTO answerDTO);
    AnswerDTO findById(Long id) throws Exception;
}
