package org.ligot.afriyan.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.ligot.afriyan.Dto.CentrePartenaireDTO;
import org.ligot.afriyan.service.ICentrePartenaire;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value ={"api/centrepartenaire"})
public class CPController {

    private final ICentrePartenaire centrePartenaire;

    public CPController(ICentrePartenaire centrePartenaire) {
        this.centrePartenaire = centrePartenaire;
    }

    @PostMapping("save")
    CentrePartenaireDTO saveCentre(@RequestBody @Valid CentrePartenaireDTO centrePartenaireDto) throws Exception {
        return centrePartenaire.save(centrePartenaireDto);
    }

    @PutMapping(value = "/update/{id}")
    CentrePartenaireDTO updateCentre(@RequestBody @Valid CentrePartenaireDTO centrePartenaireDto, @PathVariable Long id) throws Exception {
        return centrePartenaire.update(centrePartenaireDto, id);
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
    void deleteCentre (@PathVariable Long id) throws Exception{
        centrePartenaire.delete(id);
    }

    @GetMapping(value = "/getById/{id}")
    CentrePartenaireDTO listById(@PathVariable Long id) throws Exception {
        return centrePartenaire.findById(id);
    }
    @GetMapping(value = "/getByIdUser/{id}")
    CentrePartenaireDTO getById(@PathVariable Long id) throws Exception {
        return centrePartenaire.findByUserId(id);
    }

    @PutMapping("active/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public void active(@PathVariable Long id) throws Exception {
        centrePartenaire.active(id);
    }

}
