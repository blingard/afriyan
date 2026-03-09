package org.ligot.afriyan.controller;

import jakarta.annotation.security.RolesAllowed;
import org.ligot.afriyan.Dto.RapportDTO;
import org.ligot.afriyan.service.IRapport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/rapport")
public class RapportController {

    @Autowired
    IRapport rapport;

    @PostMapping(value = "/save")
    @RolesAllowed(value = {"CREATE_RAPPORT"})
    RapportDTO saveRapport(@RequestBody RapportDTO rapportDto) throws Exception {
        return rapport.save(rapportDto);
    }

    @PutMapping(value = "/update/{id}")
    @RolesAllowed(value = {"UPDATE_RAPPORT"})
    RapportDTO updateRapport(@RequestBody RapportDTO rapportDto, @PathVariable Long id) throws Exception {
        return rapport.update(rapportDto, id);
    }

    @GetMapping(value = "/list/{page}")
    @RolesAllowed(value = {"GET_RAPPORT"})
    Page<RapportDTO> listRapport(@PathVariable  int page) throws Exception {
        return rapport.list(page);
    }

    @DeleteMapping(value = "/delete/{id}")
    @RolesAllowed(value = {"DELETE_RAPPORT"})
    void deleteRapport (@PathVariable long id) throws Exception{
        rapport.delete(id);
    }

    @GetMapping(value = "/getById/{id}")
    RapportDTO listById(@PathVariable Long id) throws Exception {
        return rapport.findById(id);
    }


}
