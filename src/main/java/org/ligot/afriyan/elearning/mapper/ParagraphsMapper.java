package org.ligot.afriyan.elearning.mapper;

import org.ligot.afriyan.elearning.dto.ParagraphsDTO;
import org.ligot.afriyan.elearning.entities.Paragraphs;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ParagraphsMapper {

    Paragraphs toEntity(ParagraphsDTO paragraphsDTO);

    ParagraphsDTO toDTO(Paragraphs paragraphs);

    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "status", target = "status", ignore = true)
    void update(ParagraphsDTO paragraphsDTO, @MappingTarget Paragraphs paragraphs);

}
