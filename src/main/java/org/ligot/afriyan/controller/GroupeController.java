package org.ligot.afriyan.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.ligot.afriyan.Dto.GroupesDTO;
import org.ligot.afriyan.Dto.PageDTO;
import org.ligot.afriyan.Dto.RolesDTO;
import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.service.IGroupes;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = {"api/groupes"})
public class GroupeController {

    private final IGroupes groupes;

    public GroupeController(IGroupes groupes) {
        this.groupes = groupes;
    }

    @PostMapping
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    GroupesDTO saveGroupe(@RequestBody @Valid GroupesDTO groupesDto) throws Exception {
        return groupes.save(groupesDto);
    }

    @PutMapping(value = "/update/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    GroupesDTO updateGroupe(@RequestBody @Valid GroupesDTO groupesDto, @PathVariable Long id) throws Exception {
        return groupes.update(groupesDto, id);
    }

    @GetMapping(value = "/list")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    PageDTO<GroupesDTO> listGroupe(@RequestParam(name = "page", defaultValue = "0")  int page) throws Exception {
        return groupes.list(page);
    }

    @GetMapping
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    List<GroupesDTO> list() throws Exception {
        return groupes.list();
    }

    @GetMapping("roles")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    List<RolesDTO> listRoles() throws Exception {
        return groupes.listRoles();
    }

    @GetMapping("roles/{id}")
    List<RolesDTO> listGroupRoles(@PathVariable Long id) throws Exception {
        return groupes.listGroupRoles(id);
    }

    @DeleteMapping(value = "/delete/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    void deleteGroupe (@PathVariable Long id) throws Exception{
        groupes.delete(id);
    }

    @GetMapping(value = "/getById/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    GroupesDTO listById(@PathVariable Long id) throws Exception {
        return groupes.findById(id);
    }

    @PostMapping("add-roles/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    GroupesDTO addRole(@RequestBody @Valid Set<RolesDTO> rolesDTO, @PathVariable Long id) throws Exception {
        return groupes.addRoles(rolesDTO, id);
    }

    @PostMapping("remove-roles/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    void removeRole(@RequestBody @Valid Set<RolesDTO> rolesDTO, @PathVariable Long id) throws Exception {
        groupes.removeRoles(rolesDTO, id);
    }

    @PostMapping("add-users/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    GroupesDTO addUser(@RequestBody @Valid Set<UtilisateurDTO> utilisateurDTOS, @PathVariable Long id) throws Exception {
        return groupes.addUsers(utilisateurDTOS, id);
    }

    @PostMapping("remove-users/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    void removeUser(@RequestBody @Valid Set<UtilisateurDTO> utilisateurDTOS, @PathVariable Long id) throws Exception {
        groupes.removeUsers(utilisateurDTOS, id);
    }


}
