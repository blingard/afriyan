package org.ligot.afriyan.learn.service.impl;

import org.ligot.afriyan.Dto.CategoriesDTO;
import org.ligot.afriyan.entities.Categories;
import org.ligot.afriyan.implement.UtilsService;
import org.ligot.afriyan.learn.dto.*;
import org.ligot.afriyan.learn.entities.Chapitres;
import org.ligot.afriyan.learn.entities.Formation;
import org.ligot.afriyan.learn.entities.Modules;
import org.ligot.afriyan.learn.enumerations.FormationLevel;
import org.ligot.afriyan.learn.repository.*;
import org.ligot.afriyan.learn.service.ChapitresService;
import org.ligot.afriyan.learn.service.FormationService;
import org.ligot.afriyan.service.ICategories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class FormationServiceImpl implements FormationService {

    private final FormationRepository formationRepository;
    private final ModuleRepository moduleRepository;
    private final ChapitresService chapitresService;
    private final UserFormationEnrollmentRepository enrollmentRepository;
    private final UtilsService utilsService;
    private final ICategories categoriesService;

    public FormationServiceImpl(FormationRepository formationRepository, ModuleRepository moduleRepository, ChapitresService chapitresService, UserFormationEnrollmentRepository enrollmentRepository, UtilsService utilsService, ICategories categoriesService) {
        this.formationRepository = formationRepository;
        this.moduleRepository = moduleRepository;
        this.chapitresService = chapitresService;
        this.enrollmentRepository = enrollmentRepository;
        this.utilsService = utilsService;
        this.categoriesService = categoriesService;
    }

    @Override
    public FormationDTO createFormation(FormationCreateDTO dto) {
        Long userId = utilsService.getUser().getId();
        Categories category = categoriesService.findCategoriesById(dto.getCategories().getId());
        Formation formation = new Formation();
        formation.setTitre(dto.getTitre());
        formation.setDescription(dto.getDescription());
        formation.setImageCouverture(dto.getImageCouverture());
        formation.setNiveau(dto.getNiveau());
        formation.setDureeEstimee(dto.getDureeEstimee());
        formation.setStatus(Formation.FormationStatus.DRAFT);
        formation.setCreatedBy(userId);
        formation.setDateCreation(new Date());
        formation.setCategories(category);

        Formation saved = formationRepository.save(formation);
        return toDTO(saved);
    }

    @Override
    public FormationDTO updateFormation(String ids, FormationCreateDTO dto) {
        UUID id = UUID.fromString(ids);
        Formation formation = formationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));
        
        formation.setTitre(dto.getTitre());
        formation.setDescription(dto.getDescription());
        formation.setImageCouverture(dto.getImageCouverture());
        formation.setNiveau(dto.getNiveau());
        formation.setDureeEstimee(dto.getDureeEstimee());
        formation.setWithFinalQuiz(dto.getWithFinalQuiz() != null ? dto.getWithFinalQuiz() : false);
        formation.setDateModification(new Date());
        
        Formation saved = formationRepository.save(formation);
        return toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public FormationDTO getFormationById(String ids) {
        UUID id = UUID.fromString(ids);
        Formation formation = formationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));
        return toDTO(formation);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FormationDTO> getAllFormations(int page, int size) {
        Page<Formation> formations = formationRepository.findAll(PageRequest.of(page, size));
        return new PageImpl<>(
                formations.get().map(this::toDTO).collect(Collectors.toList()),
                formations.getPageable(),
                formations.getTotalElements()); 
    }

    @Override
    @Transactional(readOnly = true)
    public List<FormationDTO> getPublishedFormations() {
        return formationRepository.findPublishedFormations().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FormationDTO getPublishedFormationsByIdComplete(String idFormation) {
        UUID id = UUID.fromString(idFormation);
        Formation formation = formationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));
        return toDTOComplete(formation);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FormationDTO> getPublishedFormations(int page, int size) {
        Page<Formation> formations = formationRepository.findPublishedFormations(PageRequest.of(page, size));
        return new PageImpl<>(
                formations.get().map(this::toDTO).collect(Collectors.toList()),
                formations.getPageable(),
                formations.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FormationDTO> getFormationsByCreator(Long userId) {
        return formationRepository.findByCreatedBy(userId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FormationDTO> getFormationsByCreator(Long userId, int page, int size) {
        Page<Formation> formations = formationRepository.findByCreatedBy(userId, PageRequest.of(page, size));
        return new PageImpl<>(
                formations.get().map(this::toDTO).collect(Collectors.toList()),
                formations.getPageable(),
                formations.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FormationDTO> searchFormations(String keyword) {
        return formationRepository.searchFormations(keyword).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FormationDTO> searchFormations(String keyword, int page, int size) {
        Page<Formation> formations = formationRepository.searchFormations(keyword, PageRequest.of(page, size));
        return new PageImpl<>(
                formations.get().map(this::toDTO).collect(Collectors.toList()),
                formations.getPageable(),
                formations.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FormationDTO> getFormationsByLevel(FormationLevel niveau) {
        return formationRepository.findByNiveauAndPublished(niveau).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FormationDTO> getFormationsByLevel(FormationLevel niveau, int page, int size) {
        Page<Formation> formations = formationRepository.findByNiveauAndPublished(niveau, PageRequest.of(page, size));
        return new PageImpl<>(
                formations.get().map(this::toDTO).collect(Collectors.toList()),
                formations.getPageable(),
                formations.getTotalElements());
    }

    @Override
    public FormationDTO publishFormation(String idP) {
        UUID id = UUID.fromString(idP);
        Formation formation = formationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));
        formation.setStatus(Formation.FormationStatus.PUBLISHED);
        formation.setDateModification(new Date());
        Formation saved = formationRepository.save(formation);
        return toDTO(saved);
    }

    @Override
    public FormationDTO archiveFormation(String idA) {
        UUID id = UUID.fromString(idA);
        Formation formation = formationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));
        formation.setStatus(Formation.FormationStatus.ARCHIVED);
        formation.setDateModification(new Date());
        Formation saved = formationRepository.save(formation);
        return toDTO(saved);
    }

    @Override
    public void deleteFormation(String idS) {
        UUID id = UUID.fromString(idS);
        formationRepository.deleteById(id);
    }

    private FormationDTO toDTO(Formation formation) {
        FormationDTO dto = new FormationDTO();
        dto.setId(formation.getId());
        dto.setTitre(formation.getTitre());
        dto.setDescription(formation.getDescription());
        dto.setImageCouverture(formation.getImageCouverture());
        dto.setNiveau(formation.getNiveau());
        dto.setDureeEstimee(formation.getDureeEstimee());
        dto.setStatus(formation.getStatus().name());
        dto.setWithFinalQuiz(formation.getWithFinalQuiz());
        dto.setDateCreation(formation.getDateCreation());
        dto.setDateModification(formation.getDateModification());
        dto.setCreatedBy(formation.getCreatedBy());
        Categories categories = formation.getCategories();
        if (categories != null) {
            CategoriesDTO categoriesDTO = new CategoriesDTO();
            categoriesDTO.setId(categories.getId());
            categoriesDTO.setCode(categories.getCode());
            categoriesDTO.setDescription(categories.getDescription());
            dto.setCategories(categoriesDTO);
        }

        Long nombreModules = moduleRepository.countByFormationId(formation.getId());
        dto.setNombreModules(nombreModules != null ? nombreModules.intValue() : 0);

        Long nombreInscrits = enrollmentRepository.countCompletedByFormationId(formation.getId());
        dto.setNombreInscrits(nombreInscrits != null ? nombreInscrits.intValue() : 0);

        return dto;
    }

    private FormationDTO toDTOComplete(Formation formation) {
        FormationDTO dto = new FormationDTO();
        dto.setId(formation.getId());
        dto.setTitre(formation.getTitre());
        dto.setDescription(formation.getDescription());
        dto.setImageCouverture(formation.getImageCouverture());
        dto.setNiveau(formation.getNiveau());
        dto.setDureeEstimee(formation.getDureeEstimee());
        dto.setStatus(formation.getStatus().name());
        dto.setWithFinalQuiz(formation.getWithFinalQuiz());
        dto.setDateCreation(formation.getDateCreation());
        dto.setDateModification(formation.getDateModification());
        dto.setCreatedBy(formation.getCreatedBy());
        Categories categories = formation.getCategories();
        if (categories != null) {
            CategoriesDTO categoriesDTO = new CategoriesDTO();
            categoriesDTO.setId(categories.getId());
            categoriesDTO.setCode(categories.getCode());
            categoriesDTO.setDescription(categories.getDescription());
            dto.setCategories(categoriesDTO);
        }

        Long nombreModules = moduleRepository.countByFormationId(formation.getId());
        dto.setNombreModules(nombreModules != null ? nombreModules.intValue() : 0);
        moduleRepository.findByFormationIdOrderByOrdre(formation.getId()).forEach(module -> {
            ModuleDTO moduleDTO = new ModuleDTO();
            moduleDTO.setId(module.getId());
            moduleDTO.setTitre(module.getTitre());
            moduleDTO.setDescription(module.getDescription());
            moduleDTO.setDureeEstimee(module.getDureeEstimee());
            moduleDTO.setOrdre(module.getOrdre());
            moduleDTO.setWithQuiz(module.getWithQuiz());
            moduleDTO.setChapitres(chapitresService.getChapitresByModuleId(module.getId().toString()));
            dto.getModules().add(moduleDTO);
        });

        Long nombreInscrits = enrollmentRepository.countCompletedByFormationId(formation.getId());
        dto.setNombreInscrits(nombreInscrits != null ? nombreInscrits.intValue() : 0);
        return dto;
    }
}
