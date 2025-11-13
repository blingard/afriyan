package org.ligot.afriyan.elearning.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import org.ligot.afriyan.elearning.dto.ElearningScope;
import org.ligot.afriyan.elearning.dto.FormationsDTO;
import org.ligot.afriyan.elearning.service.FormationsService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Formations", description = "Gestion des Formations")
public class FormationsController {
    private final FormationsService services;

    public FormationsController(FormationsService services) {
        this.services = services;
    }

    @GetMapping("api/formations/admin/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public ResponseEntity<FormationsDTO> findFormationsAdmin(@PathVariable("id") Long id)throws Exception{
        return new ResponseEntity<>(services.findByIdAdmin(id), HttpStatus.OK);
    }
    @GetMapping("api/formations/admin/detail/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public ResponseEntity<FormationsDTO> findFormationsWithDetail(@PathVariable("id") Long id)throws Exception{
        return new ResponseEntity<>(services.findByIdAdminWithDetail(id), HttpStatus.OK);
    }
    @GetMapping("api/formations/{id}")
    public ResponseEntity<FormationsDTO> findFormations(@PathVariable("id") Long id)throws Exception{
        return new ResponseEntity<>(services.findById(id), HttpStatus.OK);
    }
    @GetMapping("api/formations/user/{id}")
    public ResponseEntity<FormationsDTO> findFormationsUser(@PathVariable("id") Long id)throws Exception{
        return new ResponseEntity<>(services.findByIdUser(id), HttpStatus.OK);
    }
    @GetMapping("api/formations/user/data/{id}")
    public ResponseEntity<ElearningScope> findFormationsUserLearn(@PathVariable("id") Long id)throws Exception{
        return new ResponseEntity<>(services.findByIdUserStatus(id), HttpStatus.OK);
    }
    @GetMapping("api/formations/user/{id}/all")
    public ResponseEntity<List<FormationsDTO>> findAllUserFormations(@PathVariable("id") Long id)throws Exception{
        return new ResponseEntity<>(services.findAllByIdUser(id), HttpStatus.OK);
    }
    @GetMapping("api/formations/user/{id}/finish")
    public ResponseEntity<List<FormationsDTO>> findFinishUserFormations(@PathVariable("id") Long id)throws Exception{
        return new ResponseEntity<>(services.findFinishByIdUser(id), HttpStatus.OK);
    }
    @GetMapping("api/formations/user/{id}/notfinish")
    public ResponseEntity<List<FormationsDTO>> findNotFinishUserFormations(@PathVariable("id") Long id)throws Exception{
        return new ResponseEntity<>(services.findNotFinishByIdUser(id), HttpStatus.OK);
    }

    @GetMapping("api/formations")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public ResponseEntity<Page<FormationsDTO>> findAllFormations(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size)throws Exception{
        return new ResponseEntity<>(services.findAll(page, size), HttpStatus.OK);
    }

    @GetMapping("api/formations/admin")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public ResponseEntity<Page<FormationsDTO>> findAllFormationsAdmin(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size)throws Exception{
        return new ResponseEntity<>(services.findAllFormationOnlyAdmin(page, size), HttpStatus.OK);
    }
    @GetMapping("api/formations/all")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public ResponseEntity<List<FormationsDTO>> findAllFormations()throws Exception{
        return new ResponseEntity<>(services.findAll(), HttpStatus.OK);
    }

    @GetMapping("api/formations/all/active")
    public ResponseEntity<List<FormationsDTO>> findAllActiveFormations()throws Exception{
        return new ResponseEntity<>(services.findAllActive(), HttpStatus.OK);
    }

    @GetMapping("api/formations/all/active/lite")
    public ResponseEntity<List<FormationsDTO>> findAllActiveFormationsLite()throws Exception{
        return new ResponseEntity<>(services.findAllActiveLite(), HttpStatus.OK);
    }

    @GetMapping("api/formations/by/active/lite/{id}")
    public ResponseEntity<FormationsDTO> findByIdActiveFormationsLite(@PathVariable("id") Long id)throws Exception{
        return new ResponseEntity<>(services.findByIdActiveFormationsLite(id), HttpStatus.OK);
    }

    @GetMapping("api/formations/all/active/lite/{categorie}")
    public ResponseEntity<List<FormationsDTO>> findAllActiveFormationsCategorieLite(@PathVariable("categorie")String categorie)throws Exception{
        return new ResponseEntity<>(services.findAllActiveByCategoryLiteByCode(categorie), HttpStatus.OK);
    }

    @PutMapping("api/formations/enable/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    public ResponseEntity<?> enable(@PathVariable("id")Long id) throws Exception{
        services.enable(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("api/formations/add_quizz/{idFormation}/{idQuizz}")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public ResponseEntity<?> addQuizz(@PathVariable("idFormation")Long idFormation, @PathVariable("idQuizz")Long idQuizz) throws Exception{
        services.addQuizz(idFormation, idQuizz);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("api/formations/disable/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    public ResponseEntity<?> disable(@PathVariable("id")Long id) throws Exception{
        services.disable(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("api/formations")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public ResponseEntity<?> create(@RequestBody FormationsDTO formationsDTO) throws Exception{
        services.create(formationsDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("api/formations/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public ResponseEntity<?> update(@PathVariable("id")Long id, @RequestBody FormationsDTO formationsDTO) throws Exception{
        services.update(id, formationsDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping("api/formations/finish/{idUser}/{idFormation}")
    public void finish(@PathVariable("idUser")Long idUser, @PathVariable("idFormation")Long idFormation) throws Exception{
        services.finishFormation(idUser, idFormation);
    }


    @GetMapping("api/formations/certificate/{idFormation}")
    public void certificate(@PathVariable("idFormation")Long idFormation) throws Exception{
        services.certificate(idFormation);
    }


    @GetMapping("api/formations/certificate/{idUser}/{idFormation}")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public void certificateAdmin(@PathVariable("idUser")Long idUser, @PathVariable("idFormation")Long idFormation) throws Exception{
        services.certificateAdmin(idUser, idFormation);
    }
}
