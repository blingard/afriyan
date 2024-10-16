package org.ligot.afriyan.elearning.repo;

import org.ligot.afriyan.elearning.entities.Chapitres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChapterRepo extends JpaRepository<Chapitres, Long> {
}
