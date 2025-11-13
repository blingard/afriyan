package org.ligot.afriyan.echo.service;

import org.ligot.afriyan.echo.WeatherTime;
import org.ligot.afriyan.echo.entities.Localities;
import org.ligot.afriyan.echo.entities.Maire;
import org.ligot.afriyan.echo.entities.Weather;
import org.ligot.afriyan.echo.entities.WeatherPrevision;
import org.ligot.afriyan.echo.repo.LocalityRepo;
import org.ligot.afriyan.echo.repo.MaireRepo;
import org.ligot.afriyan.echo.repo.PrefetRepo;
import org.ligot.afriyan.echo.repo.WeatherPrevisionRepository;
import org.ligot.afriyan.implement.TwilioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CronService {
    private final WeatherPrevisionRepository weatherPrevisionRepository;
    private final WeatherService weatherService;
    private final TwilioService twilioService;
    private final PrefetRepo prefetRepo;
    private final LocalityRepo localityRepo;
    private final MaireRepo maireRepo;


    public CronService(WeatherPrevisionRepository weatherPrevisionRepository, WeatherService weatherService, TwilioService twilioService, PrefetRepo prefetRepo, LocalityRepo localityRepo, MaireRepo maireRepo) {
        this.weatherPrevisionRepository = weatherPrevisionRepository;
        this.weatherService = weatherService;
        this.twilioService = twilioService;
        this.prefetRepo = prefetRepo;
        this.localityRepo = localityRepo;
        this.maireRepo = maireRepo;
    }
    private boolean isDateBetweenInclusive(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new RuntimeException("Date value is not define");
        }
        LocalDate dateToCheck = LocalDate.now();
        return (dateToCheck.isEqual(startDate) || dateToCheck.isAfter(startDate))
                && (dateToCheck.isEqual(endDate) || dateToCheck.isBefore(endDate));
    }
    @Scheduled(cron = "0 0 1 * * *")//create
    @Transactional
    public void runEveryDayAt7AM() {
        int page = 0;
        final int size = 100;
        Page<Localities> localitiesPage = localityRepo.findAll(PageRequest.of(page, size));
        while(!localitiesPage.isEmpty()){
            localitiesPage.getContent().parallelStream().forEach(localities -> {
                weatherService.saveWeather(localities.getLatitude(), localities.getLongitude(), localities.getCommune());
            });
            page = page +1;
            localitiesPage = localityRepo.findAll(PageRequest.of(page, size));
        }
    }

}
