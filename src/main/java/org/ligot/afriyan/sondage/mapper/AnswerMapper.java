package org.ligot.afriyan.sondage.mapper;

import org.ligot.afriyan.sondage.dto.AnswerDTO;
import org.ligot.afriyan.sondage.entities.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    Answer toEntity(AnswerDTO answerDTO);
    AnswerDTO toDTO(Answer answer);

    @Mapping(source = "id", target = "id", ignore = true)
    void update(AnswerDTO answerDTO, @MappingTarget Answer answer);

}
