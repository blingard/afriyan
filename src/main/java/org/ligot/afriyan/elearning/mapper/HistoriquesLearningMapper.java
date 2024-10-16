package org.ligot.afriyan.elearning.mapper;

import org.ligot.afriyan.elearning.dto.HistoriquesLearningDTO;
import org.ligot.afriyan.elearning.entities.HistoriquesLearning;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HistoriquesLearningMapper {

    HistoriquesLearning toEntity(HistoriquesLearningDTO historiquesLearningDTO);
    HistoriquesLearningDTO toDTO(HistoriquesLearning historiquesLearning);
}
