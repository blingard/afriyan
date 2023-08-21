package org.ligot.afriyan.controller;

import jakarta.annotation.security.RolesAllowed;
import org.ligot.afriyan.Dto.ChangePwd;
import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.entities.TypeDonne;
import org.ligot.afriyan.service.IUtilisateur;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/user")
public class UtilisateurController {
    private final IUtilisateur service;

    public UtilisateurController(IUtilisateur service) {
        this.service = service;
    }

    @PostMapping("register")
    public UtilisateurDTO register(@RequestBody UtilisateurDTO utilisateurDTO) throws Exception {
        return service.register(utilisateurDTO);
    }

    @PostMapping("admin")
    /*@RolesAllowed(value = {"SUPERADMIN"})*/
    public UtilisateurDTO create(@RequestBody UtilisateurDTO utilisateurDTO) throws Exception {
        return service.save(utilisateurDTO, utilisateurDTO.getGroupe().getId());
    }

    @GetMapping
    /*@RolesAllowed(value = {"SUPERADMIN"})*/
    public List<UtilisateurDTO> list() throws Exception {
        return service.list();
    }

    @GetMapping("list-page/{id}")
    /*@RolesAllowed(value = {"SUPERADMIN"})*/
    public Page<UtilisateurDTO> listAll(@PathVariable int id) throws Exception {
        return service.list(id);
    }

    @GetMapping("list/{idGroup}")
    /*@RolesAllowed(value = {"SUPERADMIN","ROOT"})*/
    public List<UtilisateurDTO> listAll(@PathVariable Long idGroup) throws Exception {
        return service.list(idGroup);
    }

    @DeleteMapping("/{id}")
    /*@RolesAllowed(value = {"SUPERADMIN","ROOT"})*/
    public void delete(@PathVariable Long id) throws Exception {
        service.disableUtilisateur(id);
    }

    @GetMapping("find-by-id/{id}")
    /*@RolesAllowed(value = {"SUPERADMIN"})*/
    public UtilisateurDTO findById(@PathVariable Long id) throws Exception {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    /*@RolesAllowed(value = {"SUPERADMIN"})*/
    public void update(@PathVariable Long id, @RequestBody UtilisateurDTO utilisateurDTO) throws Exception {
        service.update(utilisateurDTO, id);
    }

    @PutMapping("active/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public void activeOrDesactive(@PathVariable Long id) throws Exception {
        service.activeOrDesactive(id);
    }

    @PutMapping("change-password")
    public void changePassword(@RequestBody ChangePwd changePwd) throws Exception {
        service.changePassword(changePwd);
    }

    @PutMapping("reset-password/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public void resetPassword(@PathVariable Long id) throws Exception {
        service.resetPassword(id);
    }


    @GetMapping("dashboard")
    /*@PreAuthorize("hasRole('ADMIN')")*/
    public Map<String, Object> dashboard() throws Exception {
        return service.dashboard();
    }


}
