package org.ligot.afriyan.elearning.mapper;

import org.ligot.afriyan.elearning.dto.FormationsDTO;
import org.ligot.afriyan.elearning.entities.Formations;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface FormationsMapper {

    @Named("convertObjectToString")
    default String toString(Object value){
        return value.toString();
    }

    @Mapping(target = "chapitres", source = "chapitres", ignore = true)
    Formations toEntity(FormationsDTO formationsDTO);
    FormationsDTO toDTO(Formations formations);


    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "status", target = "status", ignore = true)
    @Mapping(source = "chapitres", target = "chapitres", ignore = true)
    @Mapping(source = "quizz", target = "quizz", ignore = true)
    void update(FormationsDTO formationsDTO, @MappingTarget Formations formations);

    /*
    default ChapitresDTO map(Chapitres chapitres) {
        ChapitresDTO chapitresDTO = new ChapitresDTO();
        chapitresDTO.setId(chapitres.getId());

        chapitresDTO.setTitle(chapitres.getTitle());
        chapitresDTO.setStatus(chapitres.isStatus());
        chapitresDTO.setParagraphes(map(chapitres.getParagraphes()));
        return chapitresDTO;
    }

    default Paragraphs map(ParagraphsDTO paragraphsDTO) {
        Paragraphs paragraphs = new Paragraphs();
        paragraphs.setId(paragraphsDTO.getId());
        paragraphs.setStatus(paragraphsDTO.isStatus());
        paragraphs.setType(paragraphsDTO.getType());
        paragraphs.setContent(paragraphsDTO.getContent().toString());
        return paragraphs;
    }

    default ParagraphsDTO map(Paragraphs paragraphs) {
        ParagraphsDTO paragraphsDTO = new ParagraphsDTO();
        paragraphsDTO.setId(paragraphs.getId());
        paragraphsDTO.setStatus(paragraphs.isStatus());
        paragraphsDTO.setType(paragraphs.getType());
        paragraphsDTO.setContent(paragraphs.getContent().toString());
        return paragraphsDTO;
    }

    default Set<Paragraphs> map(Set<ParagraphsDTO> paragraphsDTOS) {
        Set<Paragraphs> paragraphs = new HashSet<>(0);
        if (paragraphsDTOS == null)
            return paragraphs;
        if(paragraphsDTOS.isEmpty())
            return paragraphs;
        return paragraphsDTOS.stream().map(this::map).collect(Collectors.toSet());
    }*/
}
