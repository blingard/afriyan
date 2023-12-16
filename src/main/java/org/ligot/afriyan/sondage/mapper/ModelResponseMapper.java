package org.ligot.afriyan.sondage.mapper;

import org.ligot.afriyan.sondage.dto.ModelResponseDTO;
import org.ligot.afriyan.sondage.entities.ModelResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ModelResponseMapper {
    ModelResponse toEntity(ModelResponseDTO modelResponseDTO);
    ModelResponseDTO toDTO(ModelResponse modelResponse);

    @Mapping(source = "id", target = "id", ignore = true)
    void update(ModelResponseDTO modelResponseDTO, @MappingTarget ModelResponse modelResponse);
}
