package org.ligot.afriyan.elearning.repo;

import org.ligot.afriyan.elearning.entities.Paragraphs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParagraphRepo extends JpaRepository<Paragraphs, Long> {
}
