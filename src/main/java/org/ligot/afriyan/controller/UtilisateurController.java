package org.ligot.afriyan.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.ligot.afriyan.Dto.ChangePwd;
import org.ligot.afriyan.Dto.PageDTO;
import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.service.IUtilisateur;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public UtilisateurDTO register(@Valid @RequestBody UtilisateurDTO utilisateurDTO) throws Exception {
        return service.register(utilisateurDTO);
    }

    @PostMapping("admin")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public UtilisateurDTO create(@Valid @RequestBody UtilisateurDTO utilisateurDTO) throws Exception {
        return service.save(utilisateurDTO, utilisateurDTO.getGroupe().getId());
    }

    @PostMapping("admin/save")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    public UtilisateurDTO createAdmin(@Valid @RequestBody UtilisateurDTO utilisateurDTO) throws Exception {
        return service.saveAdmin(utilisateurDTO, utilisateurDTO.getGroupe().getId());
    }

    @GetMapping("admin/get/cp")
    @RolesAllowed(value = {"SUPERADMIN","ROOT","ADMIN"})
    public List<UtilisateurDTO> getUserCP() throws Exception {
        return service.getUserCP();
    }

    @GetMapping
    @RolesAllowed(value = {"SUPERADMIN","ROOT","ADMIN","GESTIONNAIRECENTRE"})
    public List<UtilisateurDTO> list() throws Exception {
        return service.list();
    }

    @GetMapping("list-page/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT","ADMIN"})
    public Page<UtilisateurDTO> listAll(@PathVariable int id) throws Exception {
        return service.list(id);
    }

    @GetMapping("list/{idGroup}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    public PageDTO<UtilisateurDTO> listAll(@PathVariable Long idGroup, @RequestParam(name = "page", defaultValue = "0")int page) throws Exception {
        return service.list(idGroup, page);
    }

    @GetMapping("search/{phone}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    public PageDTO<UtilisateurDTO> searchByPhone(@PathVariable("phone")String phone, @RequestParam(name = "page", defaultValue = "0")int page) throws Exception {
        return service.search(phone, page);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    public void delete(@PathVariable Long id) throws Exception {
        service.disableUtilisateur(id);
    }

    @GetMapping("find-by-id/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT","ADMIN"})
    public UtilisateurDTO findById(@PathVariable Long id) throws Exception {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT","ADMIN","USER","GESTIONNAIRECENTRE"})
    public void update(@PathVariable Long id, @RequestBody UtilisateurDTO utilisateurDTO) throws Exception {
        service.update(utilisateurDTO, id);
    }
    @PutMapping()
    @RolesAllowed(value = {"SUPERADMIN","ROOT","ADMIN","USER","GESTIONNAIRECENTRE"})
    public String addImage(
            @RequestParam(name = "file",required = false) MultipartFile file,
            @RequestParam( "id") Long idFormat) throws Exception {
        return service.update(file, idFormat);
    }
    @PostMapping("admin/file")
    @RolesAllowed(value = {"SUPERADMIN","ROOT","ADMIN"})
    public void addUserInFile(
            @RequestParam(name = "file",required = false) MultipartFile file) throws Exception {
        service.saveUserFile(file);
    }

    @PutMapping("active/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    public void activeOrDesactive(@PathVariable Long id) throws Exception {
        service.activeOrDesactive(id);
    }

    @PutMapping("change-password")
    @RolesAllowed(value = {"SUPERADMIN","ROOT","ADMIN","USER","GESTIONNAIRECENTRE"})
    public void changePassword(@RequestBody ChangePwd changePwd) throws Exception {
        service.changePassword(changePwd);
    }

    @PutMapping("reset-password/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public void resetPassword(@PathVariable Long id) throws Exception {
        service.resetPassword(id);
    }


    @GetMapping("dashboard")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    public Map<String, Object> dashboard() throws Exception {
        return service.dashboard();
    }

    @GetMapping("admin/user/save/status")
    @RolesAllowed(value = {"SUPERADMIN","ROOT","ADMIN"})
    public Map<String, Integer> statusFile() throws Exception {
        return service.statusListSave();
    }




}
