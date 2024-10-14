package org.ligot.afriyan.sondage.mapper;

import org.ligot.afriyan.sondage.dto.AnswerDTO;
import org.ligot.afriyan.sondage.dto.ModelResponseDTO;
import org.ligot.afriyan.sondage.entities.Answer;
import org.ligot.afriyan.sondage.entities.ModelResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    Answer toEntity(AnswerDTO answerDTO);
    AnswerDTO toDTO(Answer answer);

    @Mapping(source = "id", target = "id", ignore = true)
    void update(AnswerDTO answerDTO, @MappingTarget Answer answer);


    default ModelResponseDTO map(ModelResponse modelResponse) {
        ModelResponseDTO modelResponseDTO = new ModelResponseDTO();
        modelResponseDTO.setId(modelResponse.getId());
        modelResponseDTO.setValue(modelResponse.getValue());
        return modelResponseDTO;
    }

    default ModelResponse map(ModelResponseDTO modelResponseDTO) {
        ModelResponse modelResponse = new ModelResponse();
        modelResponse.setId(modelResponseDTO.getId());
        modelResponse.setValue(modelResponseDTO.getValue());
        return modelResponse;
    }
    default Set<ModelResponse> map(Set<ModelResponseDTO> modelResponseDTO) {
        Set<ModelResponse> modelResponse = new HashSet<>(0);
        if (modelResponseDTO == null)
            return modelResponse;
        if(modelResponseDTO.isEmpty())
            return modelResponse;
        return modelResponseDTO.stream().map(this::map).collect(Collectors.toSet());
    }

}
