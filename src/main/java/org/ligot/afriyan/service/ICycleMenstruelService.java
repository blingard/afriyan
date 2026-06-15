package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.CycleMenstruelSettingsDTO;
import org.ligot.afriyan.Dto.PeriodLogDTO;
import org.ligot.afriyan.Dto.CycleProjectionDTO;
import org.ligot.afriyan.Dto.SimulationRequestDTO;
import java.util.List;

public interface ICycleMenstruelService {
    List<CycleProjectionDTO> simulateCycle(SimulationRequestDTO dto);
    CycleMenstruelSettingsDTO saveSettings(CycleMenstruelSettingsDTO dto) throws Exception;
    CycleMenstruelSettingsDTO getSettings() throws Exception;
    PeriodLogDTO addPeriodLog(PeriodLogDTO dto) throws Exception;
    List<PeriodLogDTO> getPeriodLogs() throws Exception;
    List<CycleProjectionDTO> getProjections(int numberCycles) throws Exception;
}
