package org.ligot.afriyan.controller;

import jakarta.validation.Valid;
import org.ligot.afriyan.Dto.GroupesDTO;
import org.ligot.afriyan.Dto.RolesDTO;
import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.service.IGroupes;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = {"api/groupes"})
public class GroupeController {

    private final IGroupes groupes;

    public GroupeController(IGroupes groupes) {
        this.groupes = groupes;
    }

    @PostMapping
    GroupesDTO saveGroupe(@RequestBody @Valid GroupesDTO groupesDto) throws Exception {
        return groupes.save(groupesDto);
    }

    @PutMapping(value = "/update/{id}")
    GroupesDTO updateGroupe(@RequestBody @Valid GroupesDTO groupesDto, @PathVariable Long id) throws Exception {
        return groupes.update(groupesDto, id);
    }

    @GetMapping(value = "/list/{page}")
    Page<GroupesDTO> listGroupe(@PathVariable  int page) throws Exception {
        return groupes.list(page);
    }

    @DeleteMapping(value = "/delete/{id}")
    void deleteGroupe (@PathVariable Long id) throws Exception{
        groupes.delete(id);
    }

    @GetMapping(value = "/getById/{id}")
    GroupesDTO listById(@PathVariable Long id) throws Exception {
        return groupes.findById(id);
    }

    @PostMapping("add-roles/{id}")
    GroupesDTO addRole(@RequestBody @Valid Set<RolesDTO> rolesDTO, @PathVariable Long id) throws Exception {
        return groupes.addRoles(rolesDTO, id);
    }

    @PostMapping("remove-roles/{id}")
    void removeRole(@RequestBody @Valid Set<RolesDTO> rolesDTO, @PathVariable Long id) throws Exception {
        groupes.removeRoles(rolesDTO, id);
    }

    @PostMapping("add-users/{id}")
    GroupesDTO addUser(@RequestBody @Valid Set<UtilisateurDTO> utilisateurDTOS, @PathVariable Long id) throws Exception {
        return groupes.addUsers(utilisateurDTOS, id);
    }

    @PostMapping("remove-users/{id}")
    void removeUser(@RequestBody @Valid Set<UtilisateurDTO> utilisateurDTOS, @PathVariable Long id) throws Exception {
        groupes.removeUsers(utilisateurDTOS, id);
    }


}
