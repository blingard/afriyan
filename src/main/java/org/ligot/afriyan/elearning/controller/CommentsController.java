package org.ligot.afriyan.elearning.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.ligot.afriyan.elearning.dto.CommentsDTO;
import org.ligot.afriyan.elearning.service.CommentsServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Comments", description = "Gestion des Commentaires")
public class CommentsController {
    private final CommentsServices services;

    public CommentsController(CommentsServices services) {
        this.services = services;
    }

    @GetMapping("api/comments/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CommentsDTO>> listAllFromFormation(@PathVariable("id") Long id)throws Exception{
        return new ResponseEntity<>(services.ListAllCommentOfFormation(id), HttpStatus.OK);
    }

    @PostMapping("api/comments/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> create(@PathVariable("id")Long id, @RequestBody CommentsDTO commentsDTO) throws Exception{
        services.save(id, commentsDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
