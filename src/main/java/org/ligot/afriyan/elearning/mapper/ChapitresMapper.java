package org.ligot.afriyan.elearning.mapper;

import org.ligot.afriyan.elearning.dto.ChapitresDTO;
import org.ligot.afriyan.elearning.dto.ParagraphsDTO;
import org.ligot.afriyan.elearning.entities.Chapitres;
import org.ligot.afriyan.elearning.entities.Paragraphs;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ChapitresMapper {
    Chapitres toEntity(ChapitresDTO chapitresDTO);
    ChapitresDTO toDTO(Chapitres chapitres);

    @Mapping(source = "paragraphes", target = "paragraphes", ignore = true)
    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "status", target = "status", ignore = true)
    void update(ChapitresDTO chapitresDTO, @MappingTarget Chapitres chapitres);

    default ParagraphsDTO map(Paragraphs paragraphs) {
        ParagraphsDTO paragraphsDTO = new ParagraphsDTO();
        paragraphsDTO.setId(paragraphs.getId());
        paragraphsDTO.setStatus(paragraphs.isStatus());
        paragraphsDTO.setType(paragraphs.getType());
        paragraphsDTO.setContent(paragraphs.getContent());
        paragraphsDTO.setDescription(paragraphs.getDescription());
        return paragraphsDTO;
    }

    default Paragraphs map(ParagraphsDTO paragraphsDTO) {
        Paragraphs paragraphs = new Paragraphs();
        paragraphs.setId(paragraphsDTO.getId());
        paragraphs.setStatus(paragraphsDTO.isStatus());
        paragraphs.setType(paragraphsDTO.getType());
        paragraphs.setContent(paragraphsDTO.getContent().toString());
        paragraphs.setDescription(paragraphsDTO.getDescription());
        return paragraphs;
    }
    default Set<Paragraphs> map(Set<ParagraphsDTO> paragraphsDTOS) {
        Set<Paragraphs> paragraphs = new HashSet<>(0);
        if (paragraphsDTOS == null)
            return paragraphs;
        if(paragraphsDTOS.isEmpty())
            return paragraphs;
        return paragraphsDTOS.stream().map(this::map).collect(Collectors.toSet());
    }
}
