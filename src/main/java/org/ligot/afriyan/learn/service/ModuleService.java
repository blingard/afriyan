package org.ligot.afriyan.learn.service;

import org.ligot.afriyan.learn.dto.*;

import java.util.List;

public interface ModuleService {
    
    ModuleDTO createModule(ModuleCreateDTO dto);
    
    ModuleDTO updateModule(String id, ModuleCreateDTO dto);
    
    ModuleDTO getModuleById(String id);
    
    List<ModuleDTO> getModulesByFormationId(String formationId);
    
    void deleteModule(String id);
    
    void reorderModules(String formationId, List<String> moduleIds);
}
