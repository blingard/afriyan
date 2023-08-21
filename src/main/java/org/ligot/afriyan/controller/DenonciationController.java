package org.ligot.afriyan.controller;

import org.ligot.afriyan.Dto.DenonciationDTO;
import org.ligot.afriyan.service.IDenonciation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/denonciation")
public class DenonciationController {

    @Autowired
    IDenonciation denonciation;

    @PostMapping
    DenonciationDTO saveDenonciation(@RequestBody DenonciationDTO denonciationDto) throws Exception {
        return denonciation.save(denonciationDto);
    }

    @DeleteMapping(value = "/delete/{id}")
    void deleteDenonciation (@PathVariable long id) throws Exception{
        denonciation.delete(id);
    }

    @GetMapping(value = "/list/{page}")
    Page<DenonciationDTO> listDenonciation(@PathVariable  int page) throws Exception {
        return denonciation.getPage(page);
    }

    @GetMapping
    List<DenonciationDTO> list() throws Exception {
        return denonciation.get();
    }
    @GetMapping("find-by-id/{id}")
    DenonciationDTO findById(@PathVariable Long id) throws Exception {
        return denonciation.findById(id);
    }

    @PutMapping(value = "/update/{id}")
    DenonciationDTO updateDenonciation(@RequestBody DenonciationDTO denonciationDto, @PathVariable Long id) throws Exception {
        return denonciation.update(denonciationDto, id);
    }

}
