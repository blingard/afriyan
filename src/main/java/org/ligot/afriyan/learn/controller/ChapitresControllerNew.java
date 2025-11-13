package org.ligot.afriyan.learn.controller;

import lombok.RequiredArgsConstructor;
import org.ligot.afriyan.learn.dto.ChapitresCreateDTO;
import org.ligot.afriyan.learn.dto.ChapitresDTO;
import org.ligot.afriyan.learn.service.ChapitresService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/learn/chapitres")
@CrossOrigin(origins = "*")
public class ChapitresControllerNew {

    private final ChapitresService chapitresService;

    public ChapitresControllerNew(ChapitresService chapitresService) {
        this.chapitresService = chapitresService;
    }

    @PostMapping
    public ResponseEntity<ChapitresDTO> createChapitre(@Valid @RequestBody ChapitresCreateDTO dto) {
        ChapitresDTO created = chapitresService.createChapitre(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChapitresDTO> updateChapitre(@PathVariable String id, @Valid @RequestBody ChapitresCreateDTO dto) {
        ChapitresDTO updated = chapitresService.updateChapitre(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChapitresDTO> getChapitreById(@PathVariable String id) {
        ChapitresDTO chapitre = chapitresService.getChapitreById(id);
        return ResponseEntity.ok(chapitre);
    }

    @GetMapping("/module/{moduleId}")
    public ResponseEntity<List<ChapitresDTO>> getChapitresByModuleId(@PathVariable String moduleId) {
        List<ChapitresDTO> chapitres = chapitresService.getChapitresByModuleId(moduleId);
        return ResponseEntity.ok(chapitres);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChapitre(@PathVariable String id) {
        chapitresService.deleteChapitre(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/module/{moduleId}/reorder")
    public ResponseEntity<Void> reorderChapitres(@PathVariable String moduleId, @RequestBody List<String> chapitreIds) {
        chapitresService.reorderChapitres(moduleId, chapitreIds);
        return ResponseEntity.ok().build();
    }
}
