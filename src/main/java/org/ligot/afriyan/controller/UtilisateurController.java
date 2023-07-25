package org.ligot.afriyan.controller;

import jakarta.annotation.security.RolesAllowed;
import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.entities.TypeDonne;
import org.ligot.afriyan.service.IUtilisateur;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UtilisateurController {
    private final IUtilisateur service;

    public UtilisateurController(IUtilisateur service) {
        this.service = service;
    }

    @PostMapping
    public UtilisateurDTO register(@RequestBody UtilisateurDTO utilisateurDTO) throws Exception {
        return service.register(utilisateurDTO);
    }

    @PostMapping("admin")
    @RolesAllowed(value = {"SUPERADMIN"})
    public UtilisateurDTO create(@RequestBody UtilisateurDTO utilisateurDTO) throws Exception {
        return service.save(utilisateurDTO, utilisateurDTO.getGroupe().getId());
    }

    @GetMapping("list/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public Page<UtilisateurDTO> listAll(@PathVariable int id) throws Exception {
        return service.list(id);
    }

    @GetMapping("list/{page}/{idGroup}")
    //@RolesAllowed(value = {"SUPERADMIN"})
    public Page<UtilisateurDTO> listAll(@PathVariable int page, @PathVariable Long idGroup) throws Exception {
        return service.list(page, idGroup);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public void delete(@PathVariable Long id) throws Exception {
        service.disableUtilisateur(id);
    }

    @GetMapping("find-by-id/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public UtilisateurDTO findById(@PathVariable Long id) throws Exception {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody UtilisateurDTO utilisateurDTO) throws Exception {
        service.update(utilisateurDTO, id);
    }


}
