package org.ligot.afriyan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.ligot.afriyan.Dto.CentrePartenaireDTO;
import org.ligot.afriyan.elearning.dto.ParagraphsDTO;
import org.ligot.afriyan.service.ICentrePartenaire;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value ={"api/centrepartenaire"})
public class CPController {

    private final ICentrePartenaire centrePartenaire;

    public CPController(ICentrePartenaire centrePartenaire) {
        this.centrePartenaire = centrePartenaire;
    }

    @PostMapping("save")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public ResponseEntity<?> saveCentre(
            @RequestParam(name = "file",required = false) MultipartFile file,
            @RequestParam( "jsonData") String jsonData) throws Exception {
        CentrePartenaireDTO centrePartenaireDto = new ObjectMapper().readValue(jsonData, CentrePartenaireDTO.class);
        centrePartenaire.save(file, centrePartenaireDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    CentrePartenaireDTO updateCentre(@RequestBody @Valid CentrePartenaireDTO centrePartenaireDto, @PathVariable Long id) throws Exception {
        return centrePartenaire.update(centrePartenaireDto, id);
    }

    @PutMapping(value = "/update/{id}/{idUser}")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    void updateCentre(@PathVariable("idUser") Long idUser, @PathVariable("id") Long id) throws Exception {
        centrePartenaire.updateUser(idUser, id);
    }

    @GetMapping(value = "/list/{page}")
    Page<CentrePartenaireDTO> listCentre(@PathVariable  int page) throws Exception {
        return centrePartenaire.list(page);
    }

    @GetMapping
    List<CentrePartenaireDTO> listAll() throws Exception {
        return centrePartenaire.listAll();
    }


    @GetMapping("/localisation")
    @Operation(description = "Localisation des centre partenaire")
    List<CentrePartenaireDTO> localiserCentre() throws Exception {
        return centrePartenaire.list();
    }

    @DeleteMapping(value = "/delete/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    void deleteCentre (@PathVariable Long id) throws Exception{
        centrePartenaire.delete(id);
    }

    @GetMapping("/proches/{latitude}/{longitude}")
    public List<CentrePartenaireDTO> getCentrePartenaireProches(@PathVariable double latitude, @PathVariable double longitude) {
        double rayon = 10000.0D; // rayon de 100 km
        return centrePartenaire.trouverCPProches(latitude, longitude, rayon);
    }

    @GetMapping(value = "/getById/{id}")
    CentrePartenaireDTO listById(@PathVariable Long id) throws Exception {
        return centrePartenaire.findById(id);
    }
    @GetMapping(value = "/getByIdUser/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT","GESTIONNAIRECENTRE"})
    List<CentrePartenaireDTO> getById(@PathVariable Long id) throws Exception {
        return centrePartenaire.findByUserId(id);
    }

    @PutMapping("active/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    public void active(@PathVariable Long id) throws Exception {
        centrePartenaire.active(id);
    }

}
