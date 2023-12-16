package org.ligot.afriyan.sondage.repo;

import org.ligot.afriyan.sondage.entities.Scheduler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulerRepo extends JpaRepository<Scheduler, Long> {}
