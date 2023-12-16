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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
        answer.getValues().clear();
        if(answerDTO.getTypeResponse()== TypeResponse.OPEN){
            answerDTO.getValues().forEach(modelResponseDTO -> answer.getValues().add(modelResponseService.save(modelResponseDTO)));
        }else if(answerDTO.getTypeResponse()== TypeResponse.BINAIRY){
            answerDTO.getValues().forEach(
                    modelResponseDTO -> {
                        ModelResponse modelResponse = modelResponseService.findEntityById(modelResponseDTO.getId());
                        if(modelResponse != null)
                            answer.getValues().add(modelResponse);
                    });
        }else if(answerDTO.getTypeResponse()== TypeResponse.MULTIPLE){
            answerDTO.getValues().forEach(
                    modelResponseDTO -> {
                        ModelResponse modelResponse = modelResponseService.findEntityById(modelResponseDTO.getId());
                        if(modelResponse != null)
                            answer.getValues().add(modelResponse);
                    });
        }
        return repo.save(answer);
    }

    @Override
    public AnswerDTO findById(Long id) throws Exception {
        Answer Answer = repo.findById(id).orElseThrow(()->new Exception("Answer not found"));
        return mapper.toDTO(Answer);
    }
}
