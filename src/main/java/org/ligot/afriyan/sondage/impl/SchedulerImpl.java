package org.ligot.afriyan.sondage.impl;

import lombok.extern.slf4j.Slf4j;
import org.ligot.afriyan.sondage.dto.SchedulerDTO;
import org.ligot.afriyan.sondage.entities.Scheduler;
import org.ligot.afriyan.sondage.mapper.SchedulerMapper;
import org.ligot.afriyan.sondage.repo.SchedulerRepo;
import org.ligot.afriyan.sondage.service.SchedulerService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class SchedulerImpl implements SchedulerService {
    private final SchedulerRepo repo;
    private final SchedulerMapper mapper;

    public SchedulerImpl(SchedulerRepo repo, SchedulerMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public Scheduler save(SchedulerDTO schedulerDTO) throws Exception{
        schedulerDTO.setId(null);
        if(schedulerDTO.getStartDate().isAfter(schedulerDTO.getEndDate()))
            throw new Exception("Start date come after end date");
        return repo.save(mapper.toEntity(schedulerDTO));
    }

    @Override
    public Scheduler update(Long id, SchedulerDTO schedulerDTO) throws Exception {
        Scheduler scheduler = repo.findById(id).orElseThrow(()->new Exception("Scheduler not found"));
        if (!Objects.equals(scheduler.getId(), schedulerDTO.getId()))
            throw new Exception("Scheduler: data not match");
        mapper.update(schedulerDTO, scheduler);
        return repo.save(scheduler);
    }

    @Override
    public SchedulerDTO findById(Long id) throws Exception {
        Scheduler scheduler = repo.findById(id).orElseThrow(()->new Exception("Scheduler not found"));
        return mapper.toDTO(scheduler);
    }
}
