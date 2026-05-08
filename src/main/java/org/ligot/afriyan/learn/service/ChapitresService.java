package org.ligot.afriyan.learn.service;

import org.ligot.afriyan.learn.dto.ChapitresCreateDTO;
import org.ligot.afriyan.learn.dto.ChapitresDTO;

import java.util.List;

public interface ChapitresService {
    ChapitresDTO createChapitre(ChapitresCreateDTO dto);
    ChapitresDTO updateChapitre(String id, ChapitresCreateDTO dto);
    ChapitresDTO getChapitreById(String id);
    List<ChapitresDTO> getChapitresByModuleId(String moduleId);
    List<ChapitresDTO> getActiveChapitresByModuleId(String moduleId);
    void deleteChapitre(String id);
    void reorderChapitres(String moduleId, List<String> chapitreIds);
}
