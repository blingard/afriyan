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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class USRAJController {

    private final ICentrePartenaire centrePartenaire;

    public USRAJController(ICentrePartenaire centrePartenaire) {
        this.centrePartenaire = centrePartenaire;
    }

    @PostMapping("api/centrepartenaire/save")
    @RolesAllowed(value = {"CREATE_USRAJ"})
    public ResponseEntity<?> saveCentre(
            @RequestParam(name = "file",required = false) MultipartFile file,
            @RequestParam( "jsonData") String jsonData) throws Exception {
        CentrePartenaireDTO centrePartenaireDto = new ObjectMapper().readValue(jsonData, CentrePartenaireDTO.class);
        centrePartenaire.save(file, centrePartenaireDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "api/centrepartenaire/update/{id}")
    @RolesAllowed(value = {"UPDATE_USRAJ"})
    public CentrePartenaireDTO updateCentre(@RequestBody @Valid CentrePartenaireDTO centrePartenaireDto, @PathVariable Long id) throws Exception {
        return centrePartenaire.update(centrePartenaireDto, id);
    }

    @PutMapping(value = "api/centrepartenaire/update/{id}/{idUser}")
    @RolesAllowed(value = {"UPDATE_USRAJ_ADMIN"})
    public void updateCentre(@PathVariable("idUser") Long idUser, @PathVariable("id") Long id) throws Exception {
        centrePartenaire.updateUser(idUser, id);
    }

    @GetMapping(value = "api/centrepartenaire/list/{page}")
    @RolesAllowed(value = {"GET_USRAJ"})
    public Page<CentrePartenaireDTO> listCentre(@PathVariable  int page) throws Exception {
        return centrePartenaire.list(page);
    }

    @GetMapping("api/centrepartenaire")
    @RolesAllowed(value = {"GET_USRAJ"})
    public List<CentrePartenaireDTO> listAll() throws Exception {
        return centrePartenaire.listAll();
    }


    @GetMapping("api/centrepartenaire/localisation")
    @RolesAllowed(value = {"GET_USRAJ"})
    @Operation(description = "Localisation des centre partenaire")
    public List<CentrePartenaireDTO> localiserCentre() throws Exception {
        return centrePartenaire.list();
    }

    @DeleteMapping(value = "api/centrepartenaire/delete/{id}")
    @RolesAllowed(value = {"DELETE_USRAJ"})
    public void deleteCentre (@PathVariable Long id) throws Exception{
        centrePartenaire.delete(id);
    }

    @GetMapping("public/api/centrepartenaire/proches/{latitude}/{longitude}")
    public List<CentrePartenaireDTO> getCentrePartenaireProches(@PathVariable double latitude, @PathVariable double longitude) {
        return centrePartenaire.trouverCPProches(latitude, longitude);
    }

    @GetMapping("api/centrepartenaire/proches/{latitude}/{longitude}")
    @RolesAllowed(value = {"GET_USRAJ"})
    public List<CentrePartenaireDTO> getCentrePartenaireProchesUser(@PathVariable double latitude, @PathVariable double longitude) {
        return centrePartenaire.trouverCPProches(latitude, longitude);
    }

    @GetMapping(value = "public/api/centrepartenaire/getById/{id}")
    public CentrePartenaireDTO listById(@PathVariable Long id) throws Exception {
        return centrePartenaire.findById(id);
    }

    @GetMapping(value = "api/centrepartenaire/getById/{id}")
    @RolesAllowed(value = {"GET_USRAJ"})
    public CentrePartenaireDTO listByIdAdmin(@PathVariable Long id) throws Exception {
        return centrePartenaire.findById(id);
    }
    @GetMapping(value = "api/centrepartenaire/getByIdUser/{id}")
    @PreAuthorize("isAuthenticated()")
    public CentrePartenaireDTO getById(@PathVariable Long id) throws Exception {
        return centrePartenaire.findByUserId(id);
    }

    @PutMapping("api/centrepartenaire/active/{id}")
    @RolesAllowed(value = {"UPDATE_USRAJ"})
    public void active(@PathVariable Long id) throws Exception {
        centrePartenaire.active(id);
    }

}
