package org.ligot.afriyan.elearning.service;

import org.ligot.afriyan.elearning.dto.CommentsDTO;

import java.util.List;

public interface CommentsServices {
    void save(Long idFormation, CommentsDTO commentsDTO)throws Exception;
    List<CommentsDTO> ListAllCommentOfFormation(Long idFormation)throws Exception;
}
