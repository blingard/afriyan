package org.ligot.afriyan.controller;

import org.ligot.afriyan.Dto.SouscriptionDTO;
import org.ligot.afriyan.service.ISouscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/souscription")
public class SouscriptionController {

    @Autowired
    ISouscription souscription;

    @PostMapping(value = "/save")
    SouscriptionDTO saveSouscription(@RequestBody SouscriptionDTO souscriptionDto) throws Exception {
        return souscription.save(souscriptionDto);
    }

    @GetMapping(value = "/getByUserId/{id}")
    List<SouscriptionDTO> listByUserId(@PathVariable Long id) throws Exception {
        return souscription.findByIdUser(id);
    }

    @GetMapping(value = "/getByServiceId/{id}")
    List<SouscriptionDTO> listByServiceId(@PathVariable Long id) throws Exception {
        return souscription.findByIdService(id);
    }

    @PutMapping(value = "/update/{id}")
    SouscriptionDTO updateSouscription(@RequestBody SouscriptionDTO souscriptionDto, @PathVariable Long id) throws Exception {
        return souscription.update(souscriptionDto, id);
    }

    @GetMapping(value = "/list/{page}")
    Page<SouscriptionDTO> listSouscription(@PathVariable  int page) throws Exception {
        return souscription.list(page);
    }

    @DeleteMapping(value = "/delete/{id}")
    void deleteSouscription (@PathVariable long id) throws Exception{
        souscription.delete(id);
    }

    @GetMapping(value = "/getById/{id}")
    SouscriptionDTO listById(@PathVariable Long id) throws Exception {
        return souscription.findById(id);
    }

    @GetMapping(value = "/getByCPId/{id}")
    List<SouscriptionDTO> listByCPId(@PathVariable Long id) throws Exception {
        return souscription.findByIdCP(id);
    }


}
