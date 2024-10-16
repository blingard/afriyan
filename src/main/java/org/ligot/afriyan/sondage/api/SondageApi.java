package org.ligot.afriyan.sondage.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.ligot.afriyan.sondage.dto.*;
import org.ligot.afriyan.sondage.enumerations.EtatSondage;
import org.ligot.afriyan.sondage.enumerations.TypeUserSondage;
import org.ligot.afriyan.sondage.service.SondageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
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
    @GetMapping("/anonyme")
    public ResponseEntity<List<SondageDTO>> listAllAnomyne() throws Exception{
        return ResponseEntity.ok(service.findAllSondageByTypeUserAndState(TypeUserSondage.ANONYMOUS ,EtatSondage.ACTIVE));
    }
    @GetMapping("/formation")
    public ResponseEntity<List<SondageDTO>> listAllFormationAvailable() throws Exception{
        return ResponseEntity.ok(service.findAllSondageFormationAvaillable());
    }
    @GetMapping("/{id}")
    public ResponseEntity<SondageDTO> findByIdAnomyne(@PathVariable("id") Long id) throws Exception{
        SondageDTO sondageDTO = service.findById(id);
        if(sondageDTO.getState().equals(EtatSondage.ACTIVE) && sondageDTO.getTypeUser().equals(TypeUserSondage.ANONYMOUS))
            return ResponseEntity.ok(sondageDTO);
        else
            throw new Exception("Sondage not found");
    }
    @GetMapping("get/{id}")
    public ResponseEntity<SondageDTO> findById(@PathVariable("id") Long id) throws Exception{
        return ResponseEntity.ok(service.findById(id));
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<SondageDTO> findByIdUser(@PathVariable("id") Long id) throws Exception{
        SondageDTO sondageDTO = service.findById(id);
        if(sondageDTO.getState().equals(EtatSondage.ACTIVE) && sondageDTO.getTypeUser().equals(TypeUserSondage.USER))
            return ResponseEntity.ok(sondageDTO);
        else
            return ResponseEntity.ok(null);
    }
    @GetMapping("/user")
    public ResponseEntity<List<SondageDTO>> listAllUser() throws Exception{
        return ResponseEntity.ok(service.findAllSondageByTypeUserAndState(TypeUserSondage.USER ,EtatSondage.ACTIVE));
    }
    @GetMapping("/categories")
    public ResponseEntity<List<CategorieEntitiesDTO>> listCategories() throws Exception{
        return ResponseEntity.ok(service.findCategoriesDTO());
    }

    @PostMapping("/save")
    public ResponseEntity<SondageDTO> save(@RequestBody @Valid SondageDTO sondageDTO) throws Exception{
            return ResponseEntity.ok(service.save(sondageDTO));
    }
    @PutMapping("/update/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody @Valid SondageDTO sondageDTO) throws Exception{
        service.update(id, sondageDTO);
    }    @PutMapping("/update/{id}/{etatSondage}")
    public void updateSetStatus(@PathVariable("id") Long id, @PathVariable("etatSondage") String etatSondage) throws Exception{
        service.setStatus(id, etatSondage);
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
    @PostMapping("/pass")
    public void doSondage(@RequestBody Set<AnswerDTO> questionsDTOs) throws Exception{
        service.doSondage(questionsDTOs);
    }
    @PostMapping("/question_response")
    public void assignResponseToQuestion(@RequestBody QuestionResponseDTO questionResponseDTO) throws Exception{
        service.assignResponseToQuestion(questionResponseDTO);
    }

}
