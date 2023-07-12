package org.ligot.afriyan.controller;

import org.ligot.afriyan.Dto.CentrePartenaireDTO;
import org.ligot.afriyan.Dto.RolesDTO;
import org.ligot.afriyan.service.ICentrePartenaire;
import org.ligot.afriyan.service.IRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/roles")
public class RolesController {

    @Autowired
    IRoles rolesService;

    @PostMapping(value = "/save")
    RolesDTO saveRoles(@RequestBody RolesDTO rolesDTO) throws Exception {
        return rolesService.save(rolesDTO);
    }

    @PutMapping(value = "/update/{id}")
    RolesDTO updateRoles(@RequestBody RolesDTO rolesDTO, @PathVariable Long id) throws Exception {
        return rolesService.update(rolesDTO, id);
    }

    @GetMapping(value = "/list/{page}")
    Page<RolesDTO> listRoles(@PathVariable  int page) throws Exception {
        return rolesService.list(page);
    }

    @DeleteMapping(value = "/delete")
    void deleteRoles (@PathVariable long id) throws Exception{
        rolesService.delete(id);
    }

    @GetMapping(value = "/getById/{id}")
    RolesDTO listById(@PathVariable Long id) throws Exception {
        return rolesService.findById(id);
    }

}
