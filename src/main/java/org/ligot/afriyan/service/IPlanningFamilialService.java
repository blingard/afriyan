package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.PlanningFamilialDTO;
import org.ligot.afriyan.Dto.PlanningFamilialLogDTO;
import java.util.List;

public interface IPlanningFamilialService {
    PlanningFamilialDTO savePlanning(PlanningFamilialDTO dto) throws Exception;
    PlanningFamilialDTO getPlanning() throws Exception;
    PlanningFamilialLogDTO addLog(PlanningFamilialLogDTO dto) throws Exception;
    List<PlanningFamilialLogDTO> getLogs() throws Exception;
}
