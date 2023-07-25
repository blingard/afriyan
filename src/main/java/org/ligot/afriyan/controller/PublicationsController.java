package org.ligot.afriyan.controller;

import org.ligot.afriyan.Dto.PublicationsDTO;
import org.ligot.afriyan.service.IPublications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/publications")
public class PublicationsController {

    @Autowired
    IPublications publications;

    @PostMapping(value = "/save")
    PublicationsDTO savePublications(@RequestBody PublicationsDTO publicationsDto) throws Exception {
        return publications.save(publicationsDto);
    }

    @PutMapping(value = "/update/{id}")
    PublicationsDTO updatePublications(@RequestBody PublicationsDTO publicationsDto, @PathVariable Long id) throws Exception {
        return publications.update(publicationsDto, id);
    }

    @GetMapping(value = "/list/{page}")
    Page<PublicationsDTO> listPublications(@PathVariable  int page) throws Exception {
        return publications.list(page);
    }

    @DeleteMapping(value = "/delete/{id}")
    void deletePublications (@PathVariable long id) throws Exception{
        publications.delete(id);
    }

    @GetMapping(value = "/getById/{id}")
    PublicationsDTO listById(@PathVariable Long id) throws Exception {
        return publications.findById(id);
    }


}
