package org.ligot.afriyan.elearning.mapper;

import org.ligot.afriyan.elearning.dto.ChapitresDTO;
import org.ligot.afriyan.elearning.dto.CommentsDTO;
import org.ligot.afriyan.elearning.dto.FormationsDTO;
import org.ligot.afriyan.elearning.dto.ParagraphsDTO;
import org.ligot.afriyan.elearning.entities.Chapitres;
import org.ligot.afriyan.elearning.entities.Comments;
import org.ligot.afriyan.elearning.entities.Formations;
import org.ligot.afriyan.elearning.entities.Paragraphs;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface FormationsMapper {

    @Named("convertObjectToString")
    default String toString(Object value){
        return value.toString();
    }

    @Mapping(target = "chapitres", source = "chapitres", ignore = true)
    Formations toEntity(FormationsDTO formationsDTO);


    FormationsDTO toDTO(Formations formations);


    //Remove the chapter paragraphe
    //remove quizz information


    @Mapping(source = "chapitres", target = "chapitres", qualifiedByName = {"chapter"})
    FormationsDTO toDTOOnlyFormation(Formations formations);

    @Named("chapter")
    default Set<ChapitresDTO> setChapter(Set<Chapitres> chapitres){
        if(Objects.isNull(chapitres))
            return new HashSet<>(0);
        if(chapitres.isEmpty())
            return new HashSet<>(0);
        Set<ChapitresDTO> chapitresDTOSet = new HashSet<>();
        chapitres.forEach(chapitre -> {
            ChapitresDTO chapitresDTO = new ChapitresDTO(chapitre.getId(), chapitre.getTitle(), chapitre.isStatus()
                    ,"", getParagraph(chapitre.getParagraphes()),  getComment(chapitre.getComments()));
            chapitresDTOSet.add(chapitresDTO);
        });
        return chapitresDTOSet;
    }

    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "status", target = "status", ignore = true)
    @Mapping(source = "chapitres", target = "chapitres", ignore = true)
    @Mapping(source = "quizz", target = "quizz", ignore = true)
    @Mapping(source = "description", target = "description", ignore = true)
    void update(FormationsDTO formationsDTO, @MappingTarget Formations formations);

    default Set<ParagraphsDTO> getParagraph(Set<Paragraphs> paragraphes){
        if(Objects.isNull(paragraphes))
            return new HashSet<>(0);
        if(paragraphes.isEmpty())
            return new HashSet<>(0);
        Set<ParagraphsDTO> paragraphsDTOSet = new HashSet<>();
        paragraphes.forEach(paragraphs -> {
            ParagraphsDTO paragraphsDTO = new ParagraphsDTO(paragraphs.getId(), paragraphs.getType(),"",""
                    , paragraphs.isStatus(), paragraphs.getDescription());
            paragraphsDTOSet.add(paragraphsDTO);
        });
        return paragraphsDTOSet;
    }
    default Set<CommentsDTO> getComment(Set<Comments> comments){
        if(Objects.isNull(comments))
            return new HashSet<>(0);
        if(comments.isEmpty())
            return new HashSet<>(0);
        Set<CommentsDTO> commentsDTOSet = new HashSet<>();
        comments.forEach(comment -> {
            CommentsDTO commentsDTO = new CommentsDTO(comment.getId(), "","",null
                    , true);
            commentsDTOSet.add(commentsDTO);
        });
        return commentsDTOSet;
    }

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


