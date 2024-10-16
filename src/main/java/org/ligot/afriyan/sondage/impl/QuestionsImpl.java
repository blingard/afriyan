package org.ligot.afriyan.sondage.impl;

import lombok.extern.slf4j.Slf4j;
import org.ligot.afriyan.sondage.dto.AnswerDTO;
import org.ligot.afriyan.sondage.dto.QuestionsDTO;
import org.ligot.afriyan.sondage.entities.Answer;
import org.ligot.afriyan.sondage.entities.ModelResponse;
import org.ligot.afriyan.sondage.entities.Questions;
import org.ligot.afriyan.sondage.enumerations.TypeResponse;
import org.ligot.afriyan.sondage.mapper.QuestionsMapper;
import org.ligot.afriyan.sondage.repo.QuestionsRepo;
import org.ligot.afriyan.sondage.service.AnswerService;
import org.ligot.afriyan.sondage.service.ModelResponseService;
import org.ligot.afriyan.sondage.service.QuestionsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
@Slf4j
public class QuestionsImpl implements QuestionsService {

    private final QuestionsRepo repo;
    private final QuestionsMapper mapper;
    private final AnswerService answerService;
    private final ModelResponseService modelResponseService;

    public QuestionsImpl(QuestionsRepo repo, QuestionsMapper mapper, AnswerService answerService, ModelResponseService modelResponseService) {
        this.repo = repo;
        this.mapper = mapper;
        this.answerService = answerService;
        this.modelResponseService = modelResponseService;
    }

    @Override
    public Questions save(QuestionsDTO questionsDTO) throws Exception {
        Questions questions = mapper.toEntity(questionsDTO);
        questions.setId(null);
        return repo.save(generic(questionsDTO, questions));
    }

    @Override
    public Questions update(Long id, QuestionsDTO questionsDTO) throws Exception {
        Questions questions = repo.findById(id).orElseThrow(()->new Exception("Questions not found"));
        if (!Objects.equals(questions.getId(), questionsDTO.getId()))
            throw new Exception("Questions: data not match");
        mapper.update(questionsDTO, questions);
        return repo.save(generic(questionsDTO, questions));
    }
    private Questions generic(QuestionsDTO questionsDTO, Questions questions) throws Exception {
        Set<ModelResponse> values = new HashSet<>(0);
        questionsDTO.getModelResponses().forEach(
                answerDTO -> {
                    values.add(modelResponseService.save(answerDTO));
                }
        );
        questions.setModelResponses(values);
/*        if(questionsDTO.getTypeResponse() == TypeResponse.BINAIRY){
            if(questionsDTO.getModelResponses().size() != 2)
                throw new Exception("Cette question n'est pas Binaire");
            else {
                questionsDTO.getModelResponses().forEach(modelResponseDTO -> questions.getModelResponses().add(modelResponseService.save(modelResponseDTO)));
            }
        }else if(questionsDTO.getTypeResponse() == TypeResponse.MULTIPLE){
            if(questionsDTO.getModelResponses().size() <= 2)
                throw new Exception("Cette question n'est pas a choix multiple");
            else {
                questionsDTO.getModelResponses().forEach(modelResponseDTO -> questions.getModelResponses().add(modelResponseService.save(modelResponseDTO)));
            }
        }*/
        return questions;
    }

    @Override
    public QuestionsDTO findById(Long id) throws Exception {
        return mapper.toDTO(repo.findById(id).orElseThrow(()->new Exception("Data not found")));
    }

    @Override
    public Questions findByIdEntity(Long id) throws Exception {
        return repo.findById(id).orElseThrow(()->new Exception("Data not found"));
    }

    @Override
    public void addAnswer(Long idQuestion, AnswerDTO answerDTO) throws Exception {
        Questions questions = repo.findById(idQuestion).orElseThrow(()->new Exception("Questions not found"));
        if(questions.getTypeResponse() != answerDTO.getTypeResponse())
            throw new Exception("TypeResponse question and answer not match");
        repo.save(questions);
    }
}
