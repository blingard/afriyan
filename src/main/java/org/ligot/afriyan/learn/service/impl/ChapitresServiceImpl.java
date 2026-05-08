package org.ligot.afriyan.learn.service.impl;

import lombok.RequiredArgsConstructor;
import org.ligot.afriyan.learn.dto.ChapitresCreateDTO;
import org.ligot.afriyan.learn.dto.ChapitresDTO;
import org.ligot.afriyan.learn.entities.Chapitres;
import org.ligot.afriyan.learn.entities.Modules;
import org.ligot.afriyan.learn.repository.ChapitresRepository;
import org.ligot.afriyan.learn.repository.ModuleRepository;
import org.ligot.afriyan.learn.service.ChapitresService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChapitresServiceImpl implements ChapitresService {

    private final ChapitresRepository chapitresRepository;
    private final ModuleRepository moduleRepository;
    public ChapitresServiceImpl(ChapitresRepository chapitresRepository, ModuleRepository moduleRepository) {
        this.chapitresRepository = chapitresRepository;
        this.moduleRepository = moduleRepository;
    }

    @Override
    public ChapitresDTO createChapitre(ChapitresCreateDTO dto) {
        UUID id = UUID.fromString(dto.getModuleId());
        Modules module = moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module non trouvé"));

        Chapitres chapitre = new Chapitres();
        chapitre.setTitre(dto.getTitre());
        chapitre.setContenu(dto.getContenu());
        chapitre.setOrdre(dto.getOrdre());
        chapitre.setDureeEstimee(dto.getDureeEstimee());
        chapitre.setVideoUrl(dto.getVideoUrl());
        chapitre.setDocumentUrl(dto.getDocumentUrl());
        chapitre.setModule(module);
        chapitre.setDateCreation(new Date());

        Chapitres saved = chapitresRepository.save(chapitre);
        return toDTO(saved);
    }

    @Override
    public ChapitresDTO updateChapitre(String idChap, ChapitresCreateDTO dto) {
        UUID id = UUID.fromString(idChap);
        Chapitres chapitre = chapitresRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chapitre non trouvé"));

        chapitre.setTitre(dto.getTitre());
        chapitre.setContenu(dto.getContenu());
        chapitre.setOrdre(dto.getOrdre());
        chapitre.setDureeEstimee(dto.getDureeEstimee());
        chapitre.setVideoUrl(dto.getVideoUrl());
        chapitre.setDocumentUrl(dto.getDocumentUrl());
        chapitre.setDateModification(new Date());

        Chapitres saved = chapitresRepository.save(chapitre);
        return toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ChapitresDTO getChapitreById(String idChap) {
        UUID id = UUID.fromString(idChap);
        Chapitres chapitre = chapitresRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chapitre non trouvé"));
        return toDTO(chapitre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChapitresDTO> getChapitresByModuleId(String moduleId) {
        UUID id = UUID.fromString(moduleId);
        return chapitresRepository.findByModuleIdOrderByOrdre(id).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChapitresDTO> getActiveChapitresByModuleId(String moduleId) {
        UUID id = UUID.fromString(moduleId);
        return chapitresRepository.findByModuleIdOrderByOrdre(id).stream()
                .filter(Chapitres::isActive)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteChapitre(String idChap) {
        UUID id = UUID.fromString(idChap);
        Chapitres chapitres = chapitresRepository.findById(id).orElseThrow(()->new RuntimeException("non trouve"));
        chapitres.setActive(!chapitres.isActive());
        chapitresRepository.save(chapitres);
    }

    @Override
    public void reorderChapitres(String moduleId, List<String> chapitreIds) {
        UUID moduleUUID = UUID.fromString(moduleId);
        for (int i = 0; i < chapitreIds.size(); i++) {
            UUID chapitreUUID = UUID.fromString(chapitreIds.get(i));
            Chapitres chapitre = chapitresRepository.findById(chapitreUUID)
                    .orElseThrow(() -> new RuntimeException("Chapitre non trouvé"));
            chapitre.setOrdre(i + 1);
            chapitresRepository.save(chapitre);
        }
    }

    private ChapitresDTO toDTO(Chapitres chapitre) {
        ChapitresDTO dto = new ChapitresDTO();
        dto.setId(chapitre.getId());
        dto.setTitre(chapitre.getTitre());
        dto.setContenu(chapitre.getContenu());
        dto.setOrdre(chapitre.getOrdre());
        dto.setDureeEstimee(chapitre.getDureeEstimee());
        dto.setVideoUrl(chapitre.getVideoUrl());
        dto.setDocumentUrl(chapitre.getDocumentUrl());
        dto.setModuleId(chapitre.getModule().getId());
        dto.setDateCreation(chapitre.getDateCreation());
        dto.setDateModification(chapitre.getDateModification());
        dto.setActive(chapitre.isActive());
        return dto;
    }
}
