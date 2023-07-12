package org.ligot.afriyan.controller;

import org.ligot.afriyan.Dto.GroupesDTO;
import org.ligot.afriyan.service.IGroupes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/groupes")
public class GroupeController {

    @Autowired
    IGroupes groupes;

    @PostMapping(value = "/save")
    GroupesDTO saveGroupe(@RequestBody GroupesDTO groupesDto) throws Exception {
        return groupes.save(groupesDto);
    }

    @PutMapping(value = "/update/{id}")
    GroupesDTO updateGroupe(@RequestBody GroupesDTO groupesDto, @PathVariable Long id) throws Exception {
        return groupes.update(groupesDto, id);
    }

    @GetMapping(value = "/list/{page}")
    Page<GroupesDTO> listGroupe(@PathVariable  int page) throws Exception {
        return groupes.list(page);
    }

    @DeleteMapping(value = "/delete")
    void deleteGroupe (@PathVariable long id) throws Exception{
        groupes.delete(id);
    }

    @GetMapping(value = "/getById/{id}")
    GroupesDTO listById(@PathVariable Long id) throws Exception {
        return groupes.findById(id);
    }


}
