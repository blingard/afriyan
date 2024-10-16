package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.ThemeTraiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IThemeTraiterRepository extends JpaRepository<ThemeTraiter, Long> {
    List<ThemeTraiter> findThemeTraiterByActiveIsTrue();
}
