package org.ligot.afriyan.sondage.impl;

import lombok.extern.slf4j.Slf4j;
import org.ligot.afriyan.sondage.dto.AnswerDTO;
import org.ligot.afriyan.sondage.entities.Answer;
import org.ligot.afriyan.sondage.entities.ModelResponse;
import org.ligot.afriyan.sondage.enumerations.TypeResponse;
import org.ligot.afriyan.sondage.mapper.AnswerMapper;
import org.ligot.afriyan.sondage.repo.AnswerRepo;
import org.ligot.afriyan.sondage.service.AnswerService;
import org.ligot.afriyan.sondage.service.ModelResponseService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AnswerImpl implements AnswerService {

    private final AnswerRepo repo;
    private final AnswerMapper mapper;
    private final ModelResponseService modelResponseService;

    public AnswerImpl(AnswerRepo repo, AnswerMapper mapper, ModelResponseService modelResponseService) {
        this.repo = repo;
        this.mapper = mapper;
        this.modelResponseService = modelResponseService;
    }

    @Override
    public Answer save(AnswerDTO answerDTO) {
        Answer answer = mapper.toEntity(answerDTO);
        answer.setId(null);
        answer.setDateTime(Instant.now());
        if(answerDTO.getTypeResponse()== TypeResponse.BINAIRY){
            Long id = answerDTO.getValues().stream().collect(Collectors.toList()).get(0).getId();
            ModelResponse modelResponse = modelResponseService.findEntityById(id);
            if(modelResponse != null)
                answer.setValues(List.of(modelResponse));
        }
        return repo.save(answer);
    }

    @Override
    public AnswerDTO findById(Long id) throws Exception {
        Answer Answer = repo.findById(id).orElseThrow(()->new Exception("Answer not found"));
        return mapper.toDTO(Answer);
    }
}
