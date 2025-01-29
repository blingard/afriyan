package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.Sliders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ISlidersRepository extends JpaRepository<Sliders, Long> {
    List<Sliders> findSlidersByStatusIsTrue();
}
