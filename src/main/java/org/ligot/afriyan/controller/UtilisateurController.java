package org.ligot.afriyan.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.ligot.afriyan.Dto.*;
import org.ligot.afriyan.echo.dto.CommuneComityDTO;
import org.ligot.afriyan.echo.dto.CrppDTO;
import org.ligot.afriyan.echo.dto.MaireDTO;
import org.ligot.afriyan.echo.dto.PrefetDTO;
import org.ligot.afriyan.init.PermissionEnum;
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
    @RolesAllowed(value = {"CREATE_USER"})
    public UtilisateurDTO create(@Valid @RequestBody UtilisateurDTO utilisateurDTO) throws Exception {
        return service.save(utilisateurDTO, utilisateurDTO.getGroupe().getId());
    }

    @PostMapping("/user/admin/save")
    @RolesAllowed(value = {"CREATE_ADMIN_USER"})
    public UtilisateurDTO createAdmin(@Valid @RequestBody UtilisateurDTO utilisateurDTO) throws Exception {
        return service.saveAdmin(utilisateurDTO, utilisateurDTO.getGroupe().getId());
    }

    @PostMapping("/user/admin/save/prefet")
    @RolesAllowed(value = {"CREATE_PREFET_USER"})
    public PrefetDTO createPrefet(@Valid @RequestBody PrefetDTO prefetDTO) throws Exception {
        return service.savePrefet(prefetDTO);
    }

    @PostMapping("/user/admin/save/maire")
    @RolesAllowed(value = {"CREATE_MAIRE_USER"})
    public MaireDTO createMaire(@Valid @RequestBody MaireDTO maireDTO) throws Exception {
        return service.saveMaire(maireDTO);
    }

    @PostMapping("/user/admin/save/crrp")
    @RolesAllowed(value = {"CREATE_CCRP_USER"})
    public void createCrpp(@Valid @RequestBody CrppDTO crppDTO) throws Exception {
        service.saveCrpp(crppDTO);
    }

    @PostMapping("/user/admin/save/cc")
    @RolesAllowed(value = {"CREATE_CC_USER"})
    public void createCc(@Valid @RequestBody CommuneComityDTO communeComityDTO) throws Exception {
        service.saveCc(communeComityDTO);
    }

    @GetMapping("/user/admin/get/cp")
    public List<UtilisateurDTO> getUserCP(@RequestParam(name = "name") String name) throws Exception {
        return service.getUserCP(name);
    }

    @GetMapping("/user")
    @RolesAllowed(value = {"GET_USER"})
    public List<UtilisateurDTO> list() throws Exception {
        return service.list();
    }

    @GetMapping("/user/list-page/{id}")
    @RolesAllowed(value = {"GET_USER"})
    public Page<UtilisateurDTO> listAll(@PathVariable int id) throws Exception {
        return service.list(id);
    }

    @GetMapping("/user/list/{idGroup}")
    @RolesAllowed(value = {"GET_USER"})
    public PageDTO<UtilisateurDTO> listAll(@PathVariable Long idGroup, @RequestParam(name = "page", defaultValue = "0")int page) throws Exception {
        return service.list(idGroup, page);
    }

    @GetMapping("/user/search/{phone}")
    @RolesAllowed(value = {"GET_USER"})
    public PageDTO<UtilisateurDTO> searchByPhone(@PathVariable("phone")String phone, @RequestParam(name = "page", defaultValue = "0")int page) throws Exception {
        return service.search(phone, page);
    }

    @DeleteMapping("/user/{id}")
    @RolesAllowed(value = {"DELETE_USER"})
    public void delete(@PathVariable Long id) throws Exception {
        service.disableUtilisateur(id);
    }

    @GetMapping("/user/find-by-id/{id}")
    @RolesAllowed(value = {"GET_USER"})
    public UtilisateurDTO findById(@PathVariable Long id) throws Exception {
        return service.findById(id);
    }

    @PutMapping("/user/{id}")
    @RolesAllowed(value = {"UPDATE_USER"})
    public void update(@PathVariable Long id, @RequestBody UpdateUserDTO updateUserDTO) throws Exception {
        service.update(updateUserDTO, id);
    }

    @PutMapping("/user/user/{id}")
    @RolesAllowed(value = {"UPDATE_OWNE_USER"})
    public void update(@PathVariable Long id, @RequestBody UtilisateurDTO utilisateurDTO) throws Exception {
        service.update(utilisateurDTO, id);
    }
    @PutMapping("/user")
    @RolesAllowed(value = {"UPDATE_OWNE_USER"})
    public String addImage(
            @RequestParam(name = "file",required = false) MultipartFile file,
            @RequestParam( "id") Long idFormat) throws Exception {
        return service.update(file, idFormat);
    }
    @PostMapping("/user/admin/file")
    @RolesAllowed(value = {"UPDATE_OWNE_USER"})
    public void addUserInFile(
            @RequestParam(name = "file",required = false) MultipartFile file) throws Exception {
        service.saveUserFile(file);
    }

    @PutMapping("/user/active/{id}")
    @RolesAllowed(value = {"UPDATE_USER"})
    public void activeOrDesactive(@PathVariable Long id) throws Exception {
        service.activeOrDesactive(id);
    }

    @PutMapping("/user/change-password")
    @RolesAllowed(value = {"UPDATE_OWNE_USER"})
    public void changePassword(@RequestBody ChangePwd changePwd) throws Exception {
        service.changePassword(changePwd);
    }

    @PutMapping("user/reset-password/{id}")
    @RolesAllowed(value = {"UPDATE_USER"})
    public void resetPassword(@PathVariable Long id) throws Exception {
        service.resetPassword(id);
    }


    @GetMapping("/user/dashboard")
    @RolesAllowed(value = {"SEE_DASHBOARD"})
    public Map<String, Object> dashboard() throws Exception {
        return service.dashboard();
    }

    @GetMapping("/user/admin/user/save/status")
    @RolesAllowed(value = {"SUPERADMIN","ROOT","ADMIN"})
    public Map<String, Integer> statusFile() throws Exception {
        return service.statusListSave();
    }

    @DeleteMapping("/user/remove/{userId}/permission/{permission}")
    @RolesAllowed("PERMISSION_ROLE_MANAGE_PERMISSIONS")
    public void removePermission(@PathVariable Long userId, @PathVariable PermissionEnum permission){
        service.removePermission(userId, permission);
    }

    @PutMapping("/user/add/{userId}/permission")
    @RolesAllowed("PERMISSION_ROLE_MANAGE_PERMISSIONS")
    public void addPermission(@PathVariable Long userId, @Valid @RequestBody PermissionRequest request){
        service.addPermission(userId, request);
    }




}
