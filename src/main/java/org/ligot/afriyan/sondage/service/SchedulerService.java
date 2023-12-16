package org.ligot.afriyan.sondage.service;

import org.ligot.afriyan.sondage.dto.SchedulerDTO;
import org.ligot.afriyan.sondage.entities.Scheduler;

public interface SchedulerService {
    Scheduler save(SchedulerDTO schedulerDTO) throws Exception;
    Scheduler update(Long id, SchedulerDTO schedulerDTO) throws Exception;
    SchedulerDTO findById(Long id) throws Exception;
}
