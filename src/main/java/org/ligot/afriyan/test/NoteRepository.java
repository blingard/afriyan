package org.ligot.afriyan.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, String> {
    List<Note> findByEleveIdAndTrimestre(String eleveId, int trimestre);
    List<Note> findByEleveIdAndAnneeScolaire(String eleveId, String anneeScolaire);
}
