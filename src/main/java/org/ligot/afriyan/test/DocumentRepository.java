package org.ligot.afriyan.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String> {
    
    @Modifying
    @Transactional
    @Query("UPDATE Document d SET d.taille = :taille, d.version = d.version + 1 WHERE d.id = :id")
    void updateTailleEtVersion(String id, long taille);
}
