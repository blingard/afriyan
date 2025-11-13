package org.ligot.afriyan.partenaire.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.ligot.afriyan.partenaire.dto.PartenaireCreateDTO;
import org.ligot.afriyan.partenaire.dto.PartenaireDTO;
import org.ligot.afriyan.partenaire.service.PartenaireService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/partenaires")
@CrossOrigin(origins = "*")
public class PartenaireController {

    private final PartenaireService partenaireService;

    public PartenaireController(PartenaireService partenaireService) {
        this.partenaireService = partenaireService;
    }

    @PostMapping
    @RolesAllowed(value = {"SUPERADMIN", "ADMIN", "ROOT"})
    public ResponseEntity<PartenaireDTO> createPartenaire(@Valid @RequestBody PartenaireCreateDTO dto) {
        PartenaireDTO created = partenaireService.createPartenaire(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @RolesAllowed(value = {"SUPERADMIN", "ADMIN", "ROOT"})
    public ResponseEntity<PartenaireDTO> updatePartenaire(
            @PathVariable UUID id,
            @Valid @RequestBody PartenaireCreateDTO dto) {
        PartenaireDTO updated = partenaireService.updatePartenaire(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartenaireDTO> getPartenaireById(@PathVariable UUID id) {
        PartenaireDTO partenaire = partenaireService.getPartenaireById(id);
        return ResponseEntity.ok(partenaire);
    }

    @GetMapping
    public ResponseEntity<List<PartenaireDTO>> getAllPartenaires() {
        List<PartenaireDTO> partenaires = partenaireService.getAllPartenaires();
        return ResponseEntity.ok(partenaires);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<PartenaireDTO>> getAllPartenairesPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<PartenaireDTO> partenaires = partenaireService.getAllPartenaires(page, size);
        return ResponseEntity.ok(partenaires);
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<PartenaireDTO>> getPartenairesByStatut(@PathVariable Boolean statut) {
        List<PartenaireDTO> partenaires = partenaireService.getPartenairesByStatut(statut);
        return ResponseEntity.ok(partenaires);
    }

    @GetMapping("/statut/{statut}/page")
    public ResponseEntity<Page<PartenaireDTO>> getPartenairesByStatutPage(
            @PathVariable Boolean statut,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<PartenaireDTO> partenaires = partenaireService.getPartenairesByStatut(statut, page, size);
        return ResponseEntity.ok(partenaires);
    }

    @GetMapping("/published")
    public ResponseEntity<List<PartenaireDTO>> getPublishedPartenaires() {
        List<PartenaireDTO> partenaires = partenaireService.getPublishedPartenaires();
        return ResponseEntity.ok(partenaires);
    }

    @GetMapping("/published/page")
    public ResponseEntity<Page<PartenaireDTO>> getPublishedPartenairesPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<PartenaireDTO> partenaires = partenaireService.getPublishedPartenaires(page, size);
        return ResponseEntity.ok(partenaires);
    }

    @GetMapping("/active-published")
    public ResponseEntity<List<PartenaireDTO>> getActiveAndPublishedPartenaires() {
        List<PartenaireDTO> partenaires = partenaireService.getActiveAndPublishedPartenaires();
        return ResponseEntity.ok(partenaires);
    }

    @GetMapping("/active-published/page")
    public ResponseEntity<Page<PartenaireDTO>> getActiveAndPublishedPartenairesPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<PartenaireDTO> partenaires = partenaireService.getActiveAndPublishedPartenaires(page, size);
        return ResponseEntity.ok(partenaires);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PartenaireDTO>> searchPartenaires(@RequestParam String keyword) {
        List<PartenaireDTO> partenaires = partenaireService.searchPartenaires(keyword);
        return ResponseEntity.ok(partenaires);
    }

    @GetMapping("/search/page")
    public ResponseEntity<Page<PartenaireDTO>> searchPartenairesPage(
            @RequestParam String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<PartenaireDTO> partenaires = partenaireService.searchPartenaires(keyword, page, size);
        return ResponseEntity.ok(partenaires);
    }

    @PutMapping("/{id}/toggle-statut")
    @RolesAllowed(value = {"SUPERADMIN", "ADMIN", "ROOT"})
    public ResponseEntity<PartenaireDTO> toggleStatut(@PathVariable UUID id) {
        PartenaireDTO partenaire = partenaireService.toggleStatut(id);
        return ResponseEntity.ok(partenaire);
    }

    @PutMapping("/{id}/toggle-publish")
    @RolesAllowed(value = {"SUPERADMIN", "ADMIN", "ROOT"})
    public ResponseEntity<PartenaireDTO> togglePublish(@PathVariable UUID id) {
        PartenaireDTO partenaire = partenaireService.togglePublish(id);
        return ResponseEntity.ok(partenaire);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed(value = {"SUPERADMIN", "ADMIN", "ROOT"})
    public ResponseEntity<Void> deletePartenaire(@PathVariable UUID id) {
        partenaireService.deletePartenaire(id);
        return ResponseEntity.noContent().build();
    }
}
