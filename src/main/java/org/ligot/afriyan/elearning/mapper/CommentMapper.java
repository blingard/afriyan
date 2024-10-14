package org.ligot.afriyan.elearning.mapper;

import org.ligot.afriyan.elearning.dto.CommentsDTO;
import org.ligot.afriyan.elearning.entities.Comments;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comments toEntity(CommentsDTO commentsDTO);
    CommentsDTO toDTO(Comments comments);
}
