package org.ligot.afriyan.learn.controller;

import org.ligot.afriyan.implement.UtilsService;
import org.ligot.afriyan.learn.dto.*;
import org.ligot.afriyan.learn.enumerations.FormationLevel;
import org.ligot.afriyan.learn.service.FormationService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.security.RolesAllowed;

import java.util.List;

@RestController
public class FormationController {

    private final FormationService formationService;
    private final UtilsService utilsService;

    public FormationController(FormationService formationService, UtilsService utilsService) {
        this.formationService = formationService;
        this.utilsService = utilsService;
    }

    @PostMapping("/api/learn/formations")
    @RolesAllowed(value = {"CREATE_FORMATION"})
    public ResponseEntity<FormationDTO> createFormation(
            @RequestBody FormationCreateDTO dto) {
        // Récupérer l'ID de l'utilisateur connecté depuis l'authentification
        formationService.createFormation(dto);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PutMapping("/api/learn/formations/{id}")
    @RolesAllowed(value = {"UPDATE_FORMATION"})
    public ResponseEntity<FormationDTO> updateFormation(
            @PathVariable String id,
            @RequestBody FormationCreateDTO dto) {
        FormationDTO updated = formationService.updateFormation(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/api/learn/formations/{id}")
    @RolesAllowed(value = {"GET_FORMATION"})
    public ResponseEntity<FormationDTO> getFormationById(@PathVariable String id) {
        FormationDTO formation = formationService.getFormationById(id);
        return ResponseEntity.ok(formation);
    }

    @GetMapping("/api/learn/formations")
    @RolesAllowed(value = {"GET_FORMATION"})
    public ResponseEntity<Page<FormationDTO>> getAllFormations(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        Page<FormationDTO> formations = formationService.getAllFormations(page, size);
        return ResponseEntity.ok(formations);
    }

    @GetMapping("api/learn/formations/published")
    public ResponseEntity<List<FormationDTOSmart>> getPublishedFormations() {
        List<FormationDTOSmart> formations = formationService.getPublishedFormations();
        return ResponseEntity.ok(formations);
    }

    @GetMapping("/api/learn/formations/published/{id}")
    public ResponseEntity<FormationDTOSmart> getPublishedFormationsByIdComplete(@PathVariable String id, @RequestParam(value = "code", required = false) String code) {
        if(code != null && code.length() != 12)
            code = null;
        FormationDTOSmart formations = formationService.getPublishedFormationsByIdComplete(id, code);
        return ResponseEntity.ok(formations);
    }

    @GetMapping("/api/learn/formations/publish/{id}")
    public ResponseEntity<FormationDTOSmart> getPublishedFormationsByIdComplete(@PathVariable String id) {
        FormationDTOSmart formations = formationService.getPublishedFormationsByIdCompleted(id);
        return ResponseEntity.ok(formations);
    }

    @GetMapping("/api/learn/formations/published/page")
    @RolesAllowed(value = {"GET_FORMATION"})
    public ResponseEntity<Page<FormationDTO>> getPublishedFormationsPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<FormationDTO> formations = formationService.getPublishedFormations(page, size);
        return ResponseEntity.ok(formations);
    }

    @GetMapping("/api/learn/formations/my-formations")
    @RolesAllowed(value = {"GET_FORMATION"})
    public ResponseEntity<List<FormationDTOSmart>> getMyFormations(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        List<FormationDTOSmart> formations = formationService.getFormationsByCreator(userId);
        return ResponseEntity.ok(formations);
    }

    @GetMapping("/api/learn/formations/my-formations/page")
    @RolesAllowed(value = {"GET_FORMATION"})
    public ResponseEntity<Page<FormationDTO>> getMyFormationsPage(
            Authentication authentication,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Long userId = getUserIdFromAuth(authentication);
        Page<FormationDTO> formations = formationService.getFormationsByCreator(userId, page, size);
        return ResponseEntity.ok(formations);
    }

    @GetMapping("public/api/learn/formations/search")
    @RolesAllowed(value = {"GET_FORMATION"})
    public ResponseEntity<List<FormationDTOSmart>> searchFormations(@RequestParam String keyword) {
        List<FormationDTOSmart> formations = formationService.searchFormations(keyword);
        return ResponseEntity.ok(formations);
    }

    @GetMapping("/api/learn/formations/search/page")
    @RolesAllowed(value = {"GET_FORMATION"})
    public ResponseEntity<Page<FormationDTO>> searchFormationsPage(
            @RequestParam String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<FormationDTO> formations = formationService.searchFormations(keyword, page, size);
        return ResponseEntity.ok(formations);
    }

    @GetMapping("public/api/learn/formations/level/{niveau}")
    @RolesAllowed(value = {"GET_FORMATION"})
    public ResponseEntity<List<FormationDTOSmart>> getFormationsByLevel(@PathVariable FormationLevel niveau) {
        List<FormationDTOSmart> formations = formationService.getFormationsByLevel(niveau);
        return ResponseEntity.ok(formations);
    }

    @GetMapping("/api/learn/formations/level/{niveau}/page")
    @RolesAllowed(value = {"GET_FORMATION"})
    public ResponseEntity<Page<FormationDTO>> getFormationsByLevelPage(
            @PathVariable FormationLevel niveau,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<FormationDTO> formations = formationService.getFormationsByLevel(niveau, page, size);
        return ResponseEntity.ok(formations);
    }

    @PutMapping("/api/learn/formations/{id}/publish")
    @RolesAllowed(value = {"UPDATE_FORMATION"})
    public ResponseEntity<FormationDTOSmart> publishFormation(@PathVariable String id) {
        FormationDTOSmart formation = formationService.publishFormation(id);
        return ResponseEntity.ok(formation);
    }

    @PutMapping("/api/learn/formations/{id}/archive")
    @RolesAllowed(value = {"UPDATE_FORMATION"})
    public ResponseEntity<FormationDTO> archiveFormation(@PathVariable String id) {
        FormationDTO formation = formationService.archiveFormation(id);
        return ResponseEntity.ok(formation);
    }

    @DeleteMapping("/api/learn/formations/{id}")
    @RolesAllowed(value = {"UPDATE_FORMATION"})
    public ResponseEntity<Void> deleteFormation(@PathVariable String id) {
        //formationService.deleteFormation(id);
        return ResponseEntity.noContent().build();
    }

    private Long getUserIdFromAuth(Authentication authentication) {
        // Cette méthode doit être adaptée selon votre système d'authentification
        // Par exemple, si vous utilisez JWT ou Spring Security
        if (authentication != null && authentication.getPrincipal() != null) {
            // À adapter selon votre implémentation
            return 1L; // Placeholder
        }
        throw new RuntimeException("Utilisateur non authentifié");
    }
}
