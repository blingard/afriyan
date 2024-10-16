package org.ligot.afriyan.elearning.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import org.ligot.afriyan.elearning.dto.ChapitresDTO;
import org.ligot.afriyan.elearning.dto.FormationsDTO;
import org.ligot.afriyan.elearning.service.ChapterService;
import org.ligot.afriyan.elearning.service.FormationsService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test/api/formations")
@Tag(name = "Formations", description = "Gestion des Formations")
public class FormationsController {
    private final FormationsService services;

    public FormationsController(FormationsService services) {
        this.services = services;
    }

    @GetMapping("admin/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public ResponseEntity<FormationsDTO> findFormationsAdmin(@PathVariable("id") Long id)throws Exception{
        return new ResponseEntity<>(services.findByIdAdmin(id), HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<FormationsDTO> findFormations(@PathVariable("id") Long id)throws Exception{
        return new ResponseEntity<>(services.findById(id), HttpStatus.OK);
    }
    @GetMapping("user/{id}")
    public ResponseEntity<FormationsDTO> findFormationsUser(@PathVariable("id") Long id)throws Exception{
        return new ResponseEntity<>(services.findByIdUser(id), HttpStatus.OK);
    }
    @GetMapping("user/{id}/all")
    public ResponseEntity<List<FormationsDTO>> findAllUserFormations(@PathVariable("id") Long id)throws Exception{
        return new ResponseEntity<>(services.findAllByIdUser(id), HttpStatus.OK);
    }
    @GetMapping("user/{id}/finish")
    public ResponseEntity<List<FormationsDTO>> findFinishUserFormations(@PathVariable("id") Long id)throws Exception{
        return new ResponseEntity<>(services.findFinishByIdUser(id), HttpStatus.OK);
    }
    @GetMapping("user/{id}/notfinish")
    public ResponseEntity<List<FormationsDTO>> findNotFinishUserFormations(@PathVariable("id") Long id)throws Exception{
        return new ResponseEntity<>(services.findNotFinishByIdUser(id), HttpStatus.OK);
    }

    @GetMapping
    @RolesAllowed(value = {"SUPERADMIN"})
    public ResponseEntity<Page<FormationsDTO>> findAllFormations(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size)throws Exception{
        return new ResponseEntity<>(services.findAll(page, size), HttpStatus.OK);
    }
    @GetMapping("all")
    @RolesAllowed(value = {"SUPERADMIN"})
    public ResponseEntity<List<FormationsDTO>> findAllFormations()throws Exception{
        return new ResponseEntity<>(services.findAll(), HttpStatus.OK);
    }
    @GetMapping("all/active")
    public ResponseEntity<List<FormationsDTO>> findAllActiveFormations()throws Exception{
        return new ResponseEntity<>(services.findAllActive(), HttpStatus.OK);
    }

    @PutMapping("enable/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public ResponseEntity<?> enable(@PathVariable("id")Long id) throws Exception{
        services.enable(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("add_quizz/{idFormation}/{idQuizz}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public ResponseEntity<?> addQuizz(@PathVariable("idFormation")Long idFormation, @PathVariable("idQuizz")Long idQuizz) throws Exception{
        services.addQuizz(idFormation, idQuizz);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("disable/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public ResponseEntity<?> disable(@PathVariable("id")Long id) throws Exception{
        services.disable(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    @RolesAllowed(value = {"SUPERADMIN"})
    public ResponseEntity<?> create(@RequestBody FormationsDTO formationsDTO) throws Exception{
        services.create(formationsDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public ResponseEntity<?> update(@PathVariable("id")Long id, @RequestBody FormationsDTO formationsDTO) throws Exception{
        services.update(id, formationsDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping("finish/{idUser}/{idFormation}")
    public void finish(@PathVariable("idUser")Long idUser, @PathVariable("idFormation")Long idFormation) throws Exception{
        services.finishFormation(idUser, idFormation);
    }
}
