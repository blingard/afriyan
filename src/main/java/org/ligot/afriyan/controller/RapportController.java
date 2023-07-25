package org.ligot.afriyan.controller;

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
    RapportDTO saveRapport(@RequestBody RapportDTO rapportDto) throws Exception {
        return rapport.save(rapportDto);
    }

    @PutMapping(value = "/update/{id}")
    RapportDTO updateRapport(@RequestBody RapportDTO rapportDto, @PathVariable Long id) throws Exception {
        return rapport.update(rapportDto, id);
    }

    @GetMapping(value = "/list/{page}")
    Page<RapportDTO> listRapport(@PathVariable  int page) throws Exception {
        return rapport.list(page);
    }

    @DeleteMapping(value = "/delete/{id}")
    void deleteRapport (@PathVariable long id) throws Exception{
        rapport.delete(id);
    }

    @GetMapping(value = "/getById/{id}")
    RapportDTO listById(@PathVariable Long id) throws Exception {
        return rapport.findById(id);
    }


}
