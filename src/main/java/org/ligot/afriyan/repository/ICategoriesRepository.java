package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.Categories;
import org.ligot.afriyan.entities.Categories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICategoriesRepository extends JpaRepository<Categories, UUID> {
    Optional<Categories> findByIdAndStatusIsTrue(UUID id);
    Optional<Categories> findById(UUID id);
    Optional<Categories> findByCode(String code);
    Optional<Categories> findByCodeAndStatusIsTrue(String code);
    List<Categories> findByStatus(boolean status);
    Page<Categories> findAll(Pageable pageable);
}
