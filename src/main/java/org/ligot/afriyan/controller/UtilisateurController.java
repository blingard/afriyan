package org.ligot.afriyan.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.ligot.afriyan.Dto.ChangePwd;
import org.ligot.afriyan.Dto.PageDTO;
import org.ligot.afriyan.Dto.UpdateUserDTO;
import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.echo.dto.CommuneComityDTO;
import org.ligot.afriyan.echo.dto.CrppDTO;
import org.ligot.afriyan.echo.dto.MaireDTO;
import org.ligot.afriyan.echo.dto.PrefetDTO;
import org.ligot.afriyan.service.IUtilisateur;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
public class UtilisateurController {
    private final IUtilisateur service;

    public UtilisateurController(IUtilisateur service) {
        this.service = service;
    }

    @PostMapping("public/user/register")
    public UtilisateurDTO register(@Valid @RequestBody UtilisateurDTO utilisateurDTO) throws Exception {
        return service.register(utilisateurDTO);
    }

    @PostMapping("/user/admin")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public UtilisateurDTO create(@Valid @RequestBody UtilisateurDTO utilisateurDTO) throws Exception {
        return service.save(utilisateurDTO, utilisateurDTO.getGroupe().getId());
    }

    @PostMapping("/user/admin/save")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    public UtilisateurDTO createAdmin(@Valid @RequestBody UtilisateurDTO utilisateurDTO) throws Exception {
        return service.saveAdmin(utilisateurDTO, utilisateurDTO.getGroupe().getId());
    }

    @PostMapping("/user/admin/save/prefet")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    public PrefetDTO createPrefet(@Valid @RequestBody PrefetDTO prefetDTO) throws Exception {
        return service.savePrefet(prefetDTO);
    }

    @PostMapping("/user/admin/save/maire")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    public MaireDTO createMaire(@Valid @RequestBody MaireDTO maireDTO) throws Exception {
        return service.saveMaire(maireDTO);
    }

    @PostMapping("/user/admin/save/crrp")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    public void createCrpp(@Valid @RequestBody CrppDTO crppDTO) throws Exception {
        service.saveCrpp(crppDTO);
    }

    @PostMapping("/user/admin/save/cc")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    public void createCc(@Valid @RequestBody CommuneComityDTO communeComityDTO) throws Exception {
        service.saveCc(communeComityDTO);
    }

    @GetMapping("/user/admin/get/cp")
    @RolesAllowed(value = {"SUPERADMIN","ROOT","ADMIN"})
    public List<UtilisateurDTO> getUserCP(@RequestParam(name = "name") String name) throws Exception {
        return service.getUserCP(name);
    }

    @GetMapping("/user")
    @RolesAllowed(value = {"SUPERADMIN","ROOT","ADMIN","GESTIONNAIRECENTRE"})
    public List<UtilisateurDTO> list() throws Exception {
        return service.list();
    }

    @GetMapping("/user/list-page/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT","ADMIN"})
    public Page<UtilisateurDTO> listAll(@PathVariable int id) throws Exception {
        return service.list(id);
    }

    @GetMapping("/user/list/{idGroup}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    public PageDTO<UtilisateurDTO> listAll(@PathVariable Long idGroup, @RequestParam(name = "page", defaultValue = "0")int page) throws Exception {
        return service.list(idGroup, page);
    }

    @GetMapping("/user/search/{phone}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    public PageDTO<UtilisateurDTO> searchByPhone(@PathVariable("phone")String phone, @RequestParam(name = "page", defaultValue = "0")int page) throws Exception {
        return service.search(phone, page);
    }

    @DeleteMapping("/user/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    public void delete(@PathVariable Long id) throws Exception {
        service.disableUtilisateur(id);
    }

    @GetMapping("/user/find-by-id/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT","ADMIN"})
    public UtilisateurDTO findById(@PathVariable Long id) throws Exception {
        return service.findById(id);
    }

    @PutMapping("/user/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT","ADMIN","GESTIONNAIRECENTRE"})
    public void update(@PathVariable Long id, @RequestBody UpdateUserDTO updateUserDTO) throws Exception {
        System.err.println("693917054");
        service.update(updateUserDTO, id);
    }

    @PutMapping("/user/user/{id}")
    @RolesAllowed(value = {"USER"})
    public void update(@PathVariable Long id, @RequestBody UtilisateurDTO utilisateurDTO) throws Exception {
        service.update(utilisateurDTO, id);
    }
    @PutMapping("/user")
    @RolesAllowed(value = {"SUPERADMIN","ROOT","ADMIN","USER","GESTIONNAIRECENTRE"})
    public String addImage(
            @RequestParam(name = "file",required = false) MultipartFile file,
            @RequestParam( "id") Long idFormat) throws Exception {
        return service.update(file, idFormat);
    }
    @PostMapping("/user/admin/file")
    @RolesAllowed(value = {"SUPERADMIN","ROOT","ADMIN"})
    public void addUserInFile(
            @RequestParam(name = "file",required = false) MultipartFile file) throws Exception {
        service.saveUserFile(file);
    }

    @PutMapping("/user/active/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    public void activeOrDesactive(@PathVariable Long id) throws Exception {
        service.activeOrDesactive(id);
    }

    @PutMapping("/user/change-password")
    @RolesAllowed(value = {"SUPERADMIN","ROOT","ADMIN","USER","GESTIONNAIRECENTRE"})
    public void changePassword(@RequestBody ChangePwd changePwd) throws Exception {
        service.changePassword(changePwd);
    }

    @PutMapping("public/user/reset-password/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public void resetPassword(@PathVariable Long id) throws Exception {
        service.resetPassword(id);
    }


    @GetMapping("/user/dashboard")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    public Map<String, Object> dashboard() throws Exception {
        return service.dashboard();
    }

    @GetMapping("/user/admin/user/save/status")
    @RolesAllowed(value = {"SUPERADMIN","ROOT","ADMIN"})
    public Map<String, Integer> statusFile() throws Exception {
        return service.statusListSave();
    }




}
