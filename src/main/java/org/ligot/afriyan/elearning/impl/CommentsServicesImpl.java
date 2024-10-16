package org.ligot.afriyan.elearning.impl;

import lombok.extern.slf4j.Slf4j;
import org.ligot.afriyan.elearning.dto.CommentsDTO;
import org.ligot.afriyan.elearning.entities.Chapitres;
import org.ligot.afriyan.elearning.entities.Comments;
import org.ligot.afriyan.elearning.entities.Formations;
import org.ligot.afriyan.elearning.mapper.CommentMapper;
import org.ligot.afriyan.elearning.repo.ChapterRepo;
import org.ligot.afriyan.elearning.repo.CommentsRepo;
import org.ligot.afriyan.elearning.repo.FormationsRepo;
import org.ligot.afriyan.elearning.service.CommentsServices;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentsServicesImpl implements CommentsServices {

    private final CommentsRepo repo;
    private final CommentMapper mapper;
    private final FormationsRepo formationsRepo;
    private final ChapterRepo chapterRepo;

    public CommentsServicesImpl(CommentsRepo repo, CommentMapper mapper, FormationsRepo formationsRepo, ChapterRepo chapterRepo) {
        this.repo = repo;
        this.mapper = mapper;
        this.formationsRepo = formationsRepo;
        this.chapterRepo = chapterRepo;
    }


    @Override
    public void save(Long idFormation, CommentsDTO commentsDTO) throws Exception {
        Chapitres chapitres = getById(idFormation);
        Comments comments = mapper.toEntity(commentsDTO);
        comments.setDateCreation(Instant.now());
        comments = repo.save(comments);
        if(chapitres.getComments()==null)
            chapitres.setComments(new HashSet<>(0));
        chapitres.getComments().add(comments);
        chapterRepo.save(chapitres);
    }

    @Override
    public List<CommentsDTO> ListAllCommentOfFormation(Long idFormation) throws Exception {
        return getById(idFormation).getComments().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

/*    private Formations getById(Long id)throws Exception{
        Formations formations = formationsRepo.findById(id).orElseThrow(()->new Exception("Formations not found"));
        if(!formations.isStatus())
            new Exception("Formations not active");
        return formations;
    }*/

    private Chapitres getById(Long id)throws Exception{

        Chapitres chapitres = chapterRepo.findById(id).orElseThrow(()->new Exception("Formations not found"));
        if(!chapitres.isStatus())
            new Exception("Formations not active");
        return chapitres;
    }
}
