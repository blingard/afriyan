package org.ligot.afriyan.controller;

import org.ligot.afriyan.Dto.RendezVousDTO;
import org.ligot.afriyan.service.IRendezVous;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/rendezvous")
public class RendezVousController {

    @Autowired
    IRendezVous rendezVous;

    @PostMapping(value = "/save")
    RendezVousDTO saveRendezVous(@RequestBody RendezVousDTO rendezVousDto) throws Exception {
        return rendezVous.save(rendezVousDto);
    }

    @PutMapping(value = "/update/{id}")
    RendezVousDTO updateRendezVous(@RequestBody RendezVousDTO rendezVousDto, @PathVariable Long id) throws Exception {
        return rendezVous.update(rendezVousDto, id);
    }

    @GetMapping(value = "/list/{page}")
    Page<RendezVousDTO> listRendezVous(@PathVariable  int page) throws Exception {
        return rendezVous.list(page);
    }

    @DeleteMapping(value = "/delete")
    void deleteRendezVous (@PathVariable long id) throws Exception{
        rendezVous.delete(id);
    }

    @GetMapping(value = "/getById/{id}")
    RendezVousDTO listById(@PathVariable Long id) throws Exception {
        return rendezVous.findById(id);
    }


}
