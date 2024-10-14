package org.ligot.afriyan.sondage.mapper;

import org.ligot.afriyan.sondage.dto.QuestionsDTO;
import org.ligot.afriyan.sondage.entities.Questions;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface QuestionsMapper {
    Questions toEntity(QuestionsDTO questionsDTO);


    @Mapping(source = "modelResponses", target = "modelResponses")
    QuestionsDTO toDTO(Questions questions);

    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "typeResponse", target = "typeResponse", ignore = true)
    @Mapping(source = "modelResponses", target = "modelResponses", ignore = true)
    void update(QuestionsDTO questionsDTO, @MappingTarget Questions questions);
}
