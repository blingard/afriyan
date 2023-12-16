package org.ligot.afriyan.sondage.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.ligot.afriyan.sondage.dto.QuestionsDTO;
import org.ligot.afriyan.sondage.dto.SchedulerDTO;
import org.ligot.afriyan.sondage.dto.SondageDTO;
import org.ligot.afriyan.sondage.enumerations.EtatSondage;
import org.ligot.afriyan.sondage.service.SondageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/test/api/sondage")
@Tag(name = "Sondage", description = "Gestion des sondages")
public class SondageApi {
    private final SondageService service;

    public SondageApi(SondageService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SondageDTO>> listAll() throws Exception{
        return ResponseEntity.ok(service.findAllSondage());
    }
    @GetMapping("/by-state/{state}")
    public ResponseEntity<List<SondageDTO>> listAllByState(@PathVariable("state") String state) throws Exception{
        return ResponseEntity.ok(service.findAllSondageDTO(state));
    }
    @GetMapping("/active")
    public ResponseEntity<List<SondageDTO>> listAllActive() throws Exception{
        return ResponseEntity.ok(service.findAllSondageDTO(EtatSondage.ACTIVE.toString()));
    }

    @PostMapping("/save")
    public void save(@RequestBody @Valid SondageDTO sondageDTO) throws Exception{
        service.save(sondageDTO);
    }
    @PutMapping("/update/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody @Valid SondageDTO sondageDTO) throws Exception{
        service.update(id, sondageDTO);
    }
    @PutMapping("/schedule/{id}")
    public void schedule(@PathVariable("id") Long id, @RequestBody @Valid SchedulerDTO schedulerDTO) throws Exception{
        service.schedule(id, schedulerDTO);
    }
    @PutMapping("/archive/{id}")
    public void archive(@PathVariable("id") Long id) throws Exception{
        service.archive(id);
    }
    @PutMapping("/question/{idQuestion}/{idSondage}")
    public void updateQuestion(@PathVariable("idSondage") Long idSondage, @PathVariable("idQuestion") Long idQuestion, @RequestBody QuestionsDTO questionsDTO) throws Exception{
        service.updateQuestion(idSondage, idQuestion, questionsDTO);
    }
    @PutMapping("/pass/{id}")
    public void doSondage(@PathVariable("id") Long id, @RequestBody Set<QuestionsDTO> questionsDTOs) throws Exception{
        service.doSondage(id, questionsDTOs);
    }
}
