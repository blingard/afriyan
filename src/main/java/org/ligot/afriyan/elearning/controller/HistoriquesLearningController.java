package org.ligot.afriyan.elearning.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.ligot.afriyan.elearning.dto.HistoriquesLearningDTO;
import org.ligot.afriyan.elearning.service.HistoriquesLearningService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test/api/historiques")
@Tag(name = "HistoriquesLearning", description = "Gestion des Historiques du E-Learning")
public class HistoriquesLearningController {
    private final HistoriquesLearningService service;

    public HistoriquesLearningController(HistoriquesLearningService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody HistoriquesLearningDTO historiquesLearningDTO)throws Exception{
        service.save(historiquesLearningDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id)throws Exception{
        service.passTest(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{userId}/{formationId}")
    public ResponseEntity<?> getAll(
            @PathVariable("userId") Long userId,
            @PathVariable("formationId") Long formationId
            )throws Exception{
        return new ResponseEntity<>(service.getAllUserHistoricOfFormation(userId, formationId),HttpStatus.OK);
    }

}
