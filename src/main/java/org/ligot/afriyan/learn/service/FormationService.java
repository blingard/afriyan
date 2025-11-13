package org.ligot.afriyan.learn.service;

import org.ligot.afriyan.learn.dto.*;
import org.ligot.afriyan.learn.enumerations.FormationLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FormationService {
    
    FormationDTO createFormation(FormationCreateDTO dto);
    
    FormationDTO updateFormation(String id, FormationCreateDTO dto);
    
    FormationDTO getFormationById(String id);
    
    Page<FormationDTO> getAllFormations(int page, int size);

    List<FormationDTO> getPublishedFormations();

    FormationDTO getPublishedFormationsByIdComplete(String id);
    
    Page<FormationDTO> getPublishedFormations(int page, int size);
    
    List<FormationDTO> getFormationsByCreator(Long userId);
    
    Page<FormationDTO> getFormationsByCreator(Long userId, int page, int size);
    
    List<FormationDTO> searchFormations(String keyword);
    
    Page<FormationDTO> searchFormations(String keyword, int page, int size);
    
    List<FormationDTO> getFormationsByLevel(FormationLevel niveau);
    
    Page<FormationDTO> getFormationsByLevel(FormationLevel niveau, int page, int size);
    
    FormationDTO publishFormation(String id);
    
    FormationDTO archiveFormation(String id);
    
    void deleteFormation(String id);
}
