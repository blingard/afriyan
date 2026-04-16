package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.Gallery;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IGalleryRepository extends JpaRepository<Gallery, UUID> {
    List<Gallery> findAllByActiveIsTrue();

}