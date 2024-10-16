package org.ligot.afriyan.sondage.service;

import org.ligot.afriyan.sondage.dto.AnswerDTO;
import org.ligot.afriyan.sondage.dto.QuestionsDTO;
import org.ligot.afriyan.sondage.entities.Answer;
import org.ligot.afriyan.sondage.entities.Questions;

public interface QuestionsService {
    Questions save(QuestionsDTO questionsDTO) throws Exception;
    Questions update(Long id, QuestionsDTO questionsDTO) throws Exception;

    QuestionsDTO findById(Long id) throws Exception;

    Questions findByIdEntity(Long id) throws Exception;
    void addAnswer(Long idQuestion, AnswerDTO answerDTO) throws Exception;

}
