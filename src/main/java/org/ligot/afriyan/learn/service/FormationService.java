package org.ligot.afriyan.learn.service;

import org.ligot.afriyan.learn.dto.*;
import org.ligot.afriyan.learn.entities.Formation;
import org.ligot.afriyan.learn.enumerations.FormationLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FormationService {
    
    FormationDTO createFormation(FormationCreateDTO dto);
    
    FormationDTO updateFormation(String id, FormationCreateDTO dto);

    FormationDTO getFormationById(String id);
    
    Page<FormationDTO> getAllFormations(int page, int size);

    List<FormationDTOSmart> getPublishedFormations();

    FormationDTOSmart getPublishedFormationsByIdComplete(String id, String code);
    FormationDTOSmart getPublishedFormationsByIdCompleted(String id);

    Page<FormationDTO> getPublishedFormations(int page, int size);
    
    List<FormationDTOSmart> getFormationsByCreator(Long userId);
    
    Page<FormationDTO> getFormationsByCreator(Long userId, int page, int size);
    
    List<FormationDTOSmart> searchFormations(String keyword);
    
    Page<FormationDTO> searchFormations(String keyword, int page, int size);
    
    List<FormationDTOSmart> getFormationsByLevel(FormationLevel niveau);
    
    Page<FormationDTO> getFormationsByLevel(FormationLevel niveau, int page, int size);

    FormationDTOSmart publishFormation(String id);
    
    FormationDTO archiveFormation(String id);
    
    void deleteFormation(String id);
}
