package org.ligot.afriyan.learn.service.impl;

import org.ligot.afriyan.learn.dto.ModuleCreateDTO;
import org.ligot.afriyan.learn.dto.ModuleDTO;
import org.ligot.afriyan.learn.entities.Formation;
import org.ligot.afriyan.learn.entities.Modules;
import org.ligot.afriyan.learn.repository.FormationRepository;
import org.ligot.afriyan.learn.repository.ModuleRepository;
import org.ligot.afriyan.learn.service.ModuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final FormationRepository formationRepository;

    public ModuleServiceImpl(ModuleRepository moduleRepository, FormationRepository formationRepository) {
        this.moduleRepository = moduleRepository;
        this.formationRepository = formationRepository;
    }

    @Override
    public ModuleDTO createModule(ModuleCreateDTO dto) {
        Formation formation = formationRepository.findById(dto.getFormationId())
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));

        Modules module = new Modules();
        module.setTitre(dto.getTitre());
        module.setDescription(dto.getDescription());
        module.setOrdre(dto.getOrdre());
        module.setDureeEstimee(dto.getDureeEstimee());
        module.setFormation(formation);
        module.setDateCreation(new Date());

        Modules saved = moduleRepository.save(module);
        return toDTO(saved);
    }

    @Override
    public ModuleDTO updateModule(String id, ModuleCreateDTO dto) {
        Modules module = moduleRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Module non trouvé"));

        module.setTitre(dto.getTitre());
        module.setDescription(dto.getDescription());
        module.setOrdre(dto.getOrdre());
        module.setDureeEstimee(dto.getDureeEstimee());
        module.setWithQuiz(dto.getWithQuiz() != null ? dto.getWithQuiz() : false);
        module.setDateModification(new Date());

        Modules saved = moduleRepository.save(module);
        return toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ModuleDTO getModuleById(String id) {
        Modules module = moduleRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Module non trouvé"));
        return toDTO(module);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ModuleDTO> getModulesByFormationId(String formationId) {
        return moduleRepository.findByFormationIdOrderByOrdre(UUID.fromString(formationId)).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteModule(String id) {
        moduleRepository.deleteById(UUID.fromString(id));
    }

    @Override
    public void reorderModules(String formationId, List<String> moduleIds) {
        for (int i = 0; i < moduleIds.size(); i++) {
            Modules module = moduleRepository.findById(UUID.fromString(moduleIds.get(i)))
                    .orElseThrow(() -> new RuntimeException("Module non trouvé"));
            module.setOrdre(i + 1);
            moduleRepository.save(module);
        }
    }

    private ModuleDTO toDTO(Modules module) {
        ModuleDTO dto = new ModuleDTO();
        dto.setId(module.getId());
        dto.setTitre(module.getTitre());
        dto.setDescription(module.getDescription());
        dto.setOrdre(module.getOrdre());
        dto.setDureeEstimee(module.getDureeEstimee());
        dto.setWithQuiz(module.getWithQuiz());
        dto.setFormationId(module.getFormation().getId());
        dto.setDateCreation(module.getDateCreation());
        dto.setDateModification(module.getDateModification());
        return dto;
    }
}
