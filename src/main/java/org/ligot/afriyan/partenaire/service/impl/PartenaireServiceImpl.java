package org.ligot.afriyan.partenaire.service.impl;

import org.ligot.afriyan.partenaire.dto.PartenaireCreateDTO;
import org.ligot.afriyan.partenaire.dto.PartenaireDTO;
import org.ligot.afriyan.partenaire.entities.Partenaire;
import org.ligot.afriyan.partenaire.repository.PartenaireRepository;
import org.ligot.afriyan.partenaire.service.PartenaireService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PartenaireServiceImpl implements PartenaireService {

    private final PartenaireRepository partenaireRepository;

    public PartenaireServiceImpl(PartenaireRepository partenaireRepository) {
        this.partenaireRepository = partenaireRepository;
    }

    @Override
    public PartenaireDTO createPartenaire(PartenaireCreateDTO dto) {
        if (partenaireRepository.existsByNom(dto.getNom())) {
            throw new RuntimeException("Un partenaire avec ce nom existe déjà");
        }

        Partenaire partenaire = new Partenaire();
        partenaire.setNom(dto.getNom());
        partenaire.setDescription(dto.getDescription());
        partenaire.setImageBase64(dto.getImageBase64());
        partenaire.setStatut(dto.getStatut() != null ? dto.getStatut() : true);
        partenaire.setPublish(dto.getPublish() != null ? dto.getPublish() : false);

        Partenaire saved = partenaireRepository.save(partenaire);
        return toDTO(saved);
    }

    @Override
    public PartenaireDTO updatePartenaire(UUID id, PartenaireCreateDTO dto) {
        Partenaire partenaire = partenaireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partenaire non trouvé"));

        if (!partenaire.getNom().equals(dto.getNom()) && partenaireRepository.existsByNom(dto.getNom())) {
            throw new RuntimeException("Un partenaire avec ce nom existe déjà");
        }

        partenaire.setNom(dto.getNom());
        partenaire.setDescription(dto.getDescription());
        partenaire.setStatut(dto.getStatut() != null ? dto.getStatut() : partenaire.getStatut());
        partenaire.setPublish(dto.getPublish() != null ? dto.getPublish() : partenaire.getPublish());
        partenaire.setDateModification(new Date());

        Partenaire saved = partenaireRepository.save(partenaire);
        return toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PartenaireDTO getPartenaireById(UUID id) {
        Partenaire partenaire = partenaireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partenaire non trouvé"));
        return toDTO(partenaire);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PartenaireDTO> getAllPartenaires() {
        return partenaireRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PartenaireDTO> getAllPartenaires(int page, int size) {
        Page<Partenaire> partenaires = partenaireRepository.findAll(PageRequest.of(page, size));
        return new PageImpl<>(
                partenaires.stream().map(this::toDTO).collect(Collectors.toList()),
                partenaires.getPageable(),
                partenaires.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PartenaireDTO> getPartenairesByStatut(Boolean statut) {
        return partenaireRepository.findByStatut(statut).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PartenaireDTO> getPartenairesByStatut(Boolean statut, int page, int size) {
        Page<Partenaire> partenaires = partenaireRepository.findByStatut(statut, PageRequest.of(page, size));
        return new PageImpl<>(
                partenaires.stream().map(this::toDTO).collect(Collectors.toList()),
                partenaires.getPageable(),
                partenaires.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PartenaireDTO> getPublishedPartenaires() {
        return partenaireRepository.findByPublish(true).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PartenaireDTO> getPublishedPartenaires(int page, int size) {
        Page<Partenaire> partenaires = partenaireRepository.findByPublish(true, PageRequest.of(page, size));
        return new PageImpl<>(
                partenaires.stream().map(this::toDTO).collect(Collectors.toList()),
                partenaires.getPageable(),
                partenaires.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PartenaireDTO> getActiveAndPublishedPartenaires() {
        return partenaireRepository.findActiveAndPublished().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PartenaireDTO> getActiveAndPublishedPartenaires(int page, int size) {
        Page<Partenaire> partenaires = partenaireRepository.findActiveAndPublished(PageRequest.of(page, size));
        return new PageImpl<>(
                partenaires.stream().map(this::toDTO).collect(Collectors.toList()),
                partenaires.getPageable(),
                partenaires.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PartenaireDTO> searchPartenaires(String keyword) {
        return partenaireRepository.searchPartenaires(keyword).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PartenaireDTO> searchPartenaires(String keyword, int page, int size) {
        Page<Partenaire> partenaires = partenaireRepository.searchPartenaires(keyword, PageRequest.of(page, size));
        return new PageImpl<>(
                partenaires.stream().map(this::toDTO).collect(Collectors.toList()),
                partenaires.getPageable(),
                partenaires.getTotalElements());
    }

    @Override
    public PartenaireDTO toggleStatut(UUID id) {
        Partenaire partenaire = partenaireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partenaire non trouvé"));
        partenaire.setStatut(!partenaire.getStatut());
        partenaire.setDateModification(new Date());
        Partenaire saved = partenaireRepository.save(partenaire);
        return toDTO(saved);
    }

    @Override
    public PartenaireDTO togglePublish(UUID id) {
        Partenaire partenaire = partenaireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partenaire non trouvé"));
        partenaire.setPublish(!partenaire.getPublish());
        partenaire.setDateModification(new Date());
        Partenaire saved = partenaireRepository.save(partenaire);
        return toDTO(saved);
    }

    @Override
    public void deletePartenaire(UUID id) {
        if (!partenaireRepository.existsById(id)) {
            throw new RuntimeException("Partenaire non trouvé");
        }
        partenaireRepository.deleteById(id);
    }

    private PartenaireDTO toDTO(Partenaire partenaire) {
        PartenaireDTO dto = new PartenaireDTO();
        dto.setId(partenaire.getId());
        dto.setNom(partenaire.getNom());
        dto.setImageBase64(partenaire.getImageBase64());
        dto.setDescription(partenaire.getDescription());
        dto.setStatut(partenaire.getStatut());
        dto.setPublish(partenaire.getPublish());
        dto.setDateCreation(partenaire.getDateCreation());
        dto.setDateModification(partenaire.getDateModification());
        return dto;
    }
}
