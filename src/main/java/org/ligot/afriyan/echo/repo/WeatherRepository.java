package org.ligot.afriyan.echo.repo;

import org.ligot.afriyan.echo.WeatherTime;
import org.ligot.afriyan.echo.entities.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Repository
public interface WeatherRepository extends JpaRepository<Weather, UUID> {
    boolean existsByCommunes_IdAndDateAndWeatherTimeAndLatitudeAndLongitude(UUID id, LocalDate date, WeatherTime weatherTime, String latitude, String longitude);
    List<Weather> findAllByCommunes_IdAndDateAndWeatherTime(UUID id, LocalDate date, WeatherTime weatherTime);
    List<Weather> findAllByDateAndWeatherTime(LocalDate date, WeatherTime weatherTime);
}
