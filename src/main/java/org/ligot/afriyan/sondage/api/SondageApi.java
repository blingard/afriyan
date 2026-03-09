package org.ligot.afriyan.sondage.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.ligot.afriyan.Dto.CategoriesDTO;
import org.ligot.afriyan.Dto.PageDTO;
import org.ligot.afriyan.sondage.dto.*;
import org.ligot.afriyan.sondage.enumerations.EtatSondage;
import org.ligot.afriyan.sondage.enumerations.TypeUserSondage;
import org.ligot.afriyan.sondage.service.SondageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@Tag(name = "Sondage", description = "Gestion des sondages")
public class SondageApi {
    private final SondageService service;

    public SondageApi(SondageService service) {
        this.service = service;
    }

    @GetMapping("/api/sondage")
    @RolesAllowed(value = {"GET_SONDAGE"})
    public ResponseEntity<PageDTO<SondageDTO>> listByPage(@RequestParam(name = "page", defaultValue = "0")int page) throws Exception{
        return ResponseEntity.ok(service.findAllSondage(page));
    }
    @GetMapping("/api/sondage/by-state/{state}")
    @RolesAllowed(value = {"GET_SONDAGE"})
    public ResponseEntity<List<SondageDTO>> listAllByState(@PathVariable("state") String state) throws Exception{
        return ResponseEntity.ok(service.findAllSondageDTO(state));
    }
    @GetMapping("/api/sondage/active")
    @RolesAllowed(value = {"GET_SONDAGE"})
    public ResponseEntity<List<SondageDTO>> listAllActive() throws Exception{
        return ResponseEntity.ok(service.findAllSondageDTO(EtatSondage.ACTIVE.toString()));
    }
    @GetMapping("/public/api/sondage/anonyme")
    public ResponseEntity<List<SondageDTO>> listAllAnomyne() throws Exception{
        return ResponseEntity.ok(service.findAllSondageByTypeUserAndState(TypeUserSondage.ANONYMOUS ,EtatSondage.ACTIVE));
    }
    @GetMapping("/api/sondage/formation")
    @RolesAllowed(value = {"GET_SONDAGE"})
    public ResponseEntity<List<SondageDTO>> listAllFormationAvailable() throws Exception{
        return ResponseEntity.ok(service.findAllSondageFormationAvaillable());
    }
    @GetMapping("/public/api/sondage/{id}")
    public ResponseEntity<SondageDTO> findByIdAnomyne(@PathVariable("id") Long id) throws Exception{
        SondageDTO sondageDTO = service.findById(id);
        if(sondageDTO.getState().equals(EtatSondage.ACTIVE) && sondageDTO.getTypeUser().equals(TypeUserSondage.ANONYMOUS))
            return ResponseEntity.ok(sondageDTO);
        else
            throw new Exception("Sondage not found");
    }

    @GetMapping("/public/api/sondage/get/{id}")
    public ResponseEntity<SondageDTO> findById(@PathVariable("id") Long id) throws Exception{
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/api/sondage/get/{id}")
    @RolesAllowed(value = {"GET_SONDAGE"})
    public ResponseEntity<SondageDTO> findByIdAdmin(@PathVariable("id") Long id) throws Exception{
        return ResponseEntity.ok(service.findByIdAdmin(id));
    }
    @GetMapping("/api/sondage/user/{id}")
    @RolesAllowed(value = {"GET_SONDAGE"})
    public ResponseEntity<SondageDTO> findByIdUser(@PathVariable("id") Long id) throws Exception{
        SondageDTO sondageDTO = service.findById(id);
        if(sondageDTO.getState().equals(EtatSondage.ACTIVE) && sondageDTO.getTypeUser().equals(TypeUserSondage.USER))
            return ResponseEntity.ok(sondageDTO);
        else
            throw new Exception("Sondage not found");
    }
    @GetMapping("/api/sondage/formation/{id}")
    public ResponseEntity<SondageDTO> findByIdFormation(@PathVariable("id") Long id) throws Exception{
        SondageDTO sondageDTO = service.findByIdToPassTest(id);
        if(sondageDTO.getState().equals(EtatSondage.ACTIVE) && sondageDTO.getTypeUser().equals(TypeUserSondage.FORMATION))
            return ResponseEntity.ok(sondageDTO);
        else
            throw new Exception("Quizz not found");
    }
    @GetMapping("/api/sondage/user")
    @RolesAllowed(value = {"GET_SONDAGE"})
    public ResponseEntity<List<SondageDTO>> listAllUser() throws Exception{
        return ResponseEntity.ok(service.findAllSondageByTypeUserAndState(TypeUserSondage.USER ,EtatSondage.ACTIVE));
    }
    @GetMapping("/api/sondage/categories")
    @RolesAllowed(value = {"GET_CATEGORIE"})
    public ResponseEntity<List<CategoriesDTO>> listCategories() throws Exception{
        return ResponseEntity.ok(service.findCategoriesDTO());
    }

    @PostMapping("/api/sondage/save")
    @RolesAllowed(value = {"CREATE_SONDAGE"})
    public ResponseEntity<SondageDTO> save(@RequestBody @Valid SondageDTO sondageDTO) throws Exception{
            return ResponseEntity.ok(service.save(sondageDTO));
    }
    @PutMapping("/api/sondage/update/{id}")
    @RolesAllowed(value = {"UPDATE_SONDAGE"})
    public void update(@PathVariable("id") Long id, @RequestBody @Valid SondageDTO sondageDTO) throws Exception{
        service.update(id, sondageDTO);
    }
    @PutMapping("/api/sondage/update/{id}/{etatSondage}")
    @RolesAllowed(value = {"UPDATE_SONDAGE"})
    public void updateSetStatus(@PathVariable("id") Long id, @PathVariable("etatSondage") String etatSondage) throws Exception{
        service.setStatus(id, etatSondage);
    }
    @PutMapping("/api/sondage/schedule/{id}")
    @RolesAllowed(value = {"UPDATE_SONDAGE"})
    public void schedule(@PathVariable("id") Long id, @RequestBody @Valid SchedulerDTO schedulerDTO) throws Exception{
        service.schedule(id, schedulerDTO);
    }
    @PutMapping("/api/sondage/archive/{id}")
    @RolesAllowed(value = {"UPDATE_SONDAGE"})
    public void archive(@PathVariable("id") Long id) throws Exception{
        service.archive(id);
    }
    @PutMapping("/api/sondage/question/{idQuestion}/{idSondage}")
    @RolesAllowed(value = {"UPDATE_SONDAGE"})
    public void updateQuestion(@PathVariable("idSondage") Long idSondage, @PathVariable("idQuestion") Long idQuestion, @RequestBody QuestionsDTO questionsDTO) throws Exception{
        service.updateQuestion(idSondage, idQuestion, questionsDTO);
    }
    @PostMapping("public/api/sondage/pass")
    public void doSondage(@RequestBody Set<AnswerDTO> questionsDTOs) throws Exception{
        service.doSondage(questionsDTOs);
    }
    @PostMapping("/api/sondage/pass/elearning")
    public Map<String, Object> elearningExam(@RequestBody Set<AnswerDTO> questionsDTOs) throws Exception{
        return service.elearningExam(questionsDTOs);
    }
    @GetMapping("/api/sondage/can_pass/{idSondage}")
    public void elearningExam(@PathVariable("idSondage") Long idSondage) throws Exception{
        service.elearningExam(idSondage);
    }
    @GetMapping("/api/sondage/result")
    public List<?> elearningExam() throws Exception{
        return service.elearningExamResult();
    }
/*    @PostMapping("/question_response")
    public void assignResponseToQuestion(@RequestBody QuestionResponseDTO questionResponseDTO) throws Exception{
        service.assignResponseToQuestion(questionResponseDTO);
    }*/
    @PostMapping("/api/sondage/question_response")
    @RolesAllowed(value = {"UPDATE_SONDAGE"})
    public void assignResponseToQuestion(@RequestBody QuestionResponseMap questionResponseDTO) throws Exception{
        service.assignResponseToQuestion(questionResponseDTO);
    }

}
