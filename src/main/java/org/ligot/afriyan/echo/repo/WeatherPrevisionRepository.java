package org.ligot.afriyan.echo.repo;

import org.ligot.afriyan.echo.entities.WeatherPrevision;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WeatherPrevisionRepository extends JpaRepository<WeatherPrevision, UUID> {
    List<WeatherPrevision> findWeatherPrevisionByCommunes_IdAndActive(UUID communeId, boolean active);
    List<WeatherPrevision> findWeatherPrevisionByStartDateLessThanEqualAndEndDateGreaterThanEqualAndActiveAndCommunes_Id(LocalDate date1, LocalDate date2, boolean active, UUID communeId, Pageable pageable);
    List<WeatherPrevision> findWeatherPrevisionByActive(boolean active);
    Page<WeatherPrevision> findWeatherPrevisionByActive(boolean active, Pageable pageable);
    List<WeatherPrevision> findWeatherPrevisionByStartDateLessThanEqualAndEndDateGreaterThanEqualAndActive(LocalDate date1, LocalDate date2, boolean active, Pageable pageable);

    long countWeatherPrevisionsByCommunes_IdAndStartDateEqualsAndActiveIsTrue(UUID communeId, LocalDate localDate);

}
