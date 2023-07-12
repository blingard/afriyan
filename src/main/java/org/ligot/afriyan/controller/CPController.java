package org.ligot.afriyan.controller;

import org.ligot.afriyan.Dto.CentrePartenaireDTO;
import org.ligot.afriyan.service.ICentrePartenaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/centrepartenaire")
public class CPController {

    @Autowired
    ICentrePartenaire centrePartenaire;

    @PostMapping(value = "/save")
    CentrePartenaireDTO saveCentre(@RequestBody CentrePartenaireDTO centrePartenaireDto) throws Exception {
        return centrePartenaire.save(centrePartenaireDto);
    }

    @PutMapping(value = "/update/{id}")
    CentrePartenaireDTO updateCentre(@RequestBody CentrePartenaireDTO centrePartenaireDto, @PathVariable Long id) throws Exception {
        return centrePartenaire.update(centrePartenaireDto, id);
    }

    @GetMapping(value = "/list/{page}")
    Page<CentrePartenaireDTO> listCentre(@PathVariable  int page) throws Exception {
        return centrePartenaire.list(page);
    }

    @DeleteMapping(value = "/delete")
    void deleteCentre (@PathVariable long id) throws Exception{
        centrePartenaire.delete(id);
    }

    @GetMapping(value = "/getById/{id}")
    CentrePartenaireDTO listById(@PathVariable Long id) throws Exception {
        return centrePartenaire.findById(id);
    }

}
