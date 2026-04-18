package org.ligot.afriyan.echo.service;

import kong.unirest.GenericType;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.ligot.afriyan.echo.WeatherTime;
import org.ligot.afriyan.echo.dto.WeatherRecord;
import org.ligot.afriyan.echo.dto.WeatherResponse;
import org.ligot.afriyan.echo.entities.*;
import org.ligot.afriyan.echo.mapper.WeatherMapper;
import org.ligot.afriyan.echo.repo.*;
import org.ligot.afriyan.entities.Departements;
import org.ligot.afriyan.entities.Groupes;
import org.ligot.afriyan.entities.Roles;
import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.implement.UtilsService;
import org.ligot.afriyan.init.RolesName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    @Value("${weather.url}")
    private String URL;

    private static final String WEATHER_API_URL = "http://wttr.in/{latitude},{longitude}?format=j1";
    //private final WeatherPrevisionRepository weatherPrevisionRepository;
    //private final WeatherPrevisionMapper weatherPrevisionMapper;
    private final WeatherMapper weatherMapper;

    private final WeatherRepository weatherRepository;
    private final MaireRepo maireRepo;
    private final DepartementsRepo departementsRepo;
    private final PrefetRepo prefetRepo;
    private final UtilsService utilsService;
    private final CommuneRepo communeRepo;

    public WeatherService(WeatherMapper weatherMapper, WeatherRepository weatherRepository, MaireRepo maireRepo, DepartementsRepo departementsRepo, PrefetRepo prefetRepo, UtilsService utilsService, CommuneRepo communeRepo) {
        this.weatherMapper = weatherMapper;
        this.weatherRepository = weatherRepository;
        this.maireRepo = maireRepo;
        this.departementsRepo = departementsRepo;
        this.prefetRepo = prefetRepo;
        this.utilsService = utilsService;
        this.communeRepo = communeRepo;
    }

    @Transactional
    public void saveWeather(String latitude, String longitude, Communes communes){
        try {
            System.err.println(WEATHER_API_URL);
            System.err.println("latitude: " +latitude);
            System.err.println("longitude: " +longitude);
            HttpResponse<WeatherResponse> response = Unirest.get(WEATHER_API_URL)
                    .routeParam("latitude", latitude)
                    .routeParam("longitude", longitude)
                    .asObject(new GenericType<>() {});
            if (!response.isSuccess()) {
                logger.error("Erreur API météo - Code: {}, Message: {}",
                        response.getStatus(), response.getStatusText());
                return;
            }
            WeatherResponse weatherResponse = response.getBody();
            Set<Weather> weatherSet = new HashSet<>();
            if(weatherResponse!=null){
                if(weatherResponse.getWeather() != null ||weatherResponse.getWeather().size()>0){

                    weatherResponse.getWeather().forEach(weatherDTO -> {
                        final String dateString = weatherDTO.getDate();
                        weatherDTO.getHourly().forEach(hourly -> {
                            String time = hourly.getTime();
                            if ("900".equals(time) || "1200".equals(time) || "1800".equals(time)) {
                                WeatherTime period = switch (time) {
                                    case "900" -> WeatherTime.MORNING;
                                    case "1200" -> WeatherTime.NOON;
                                    case "1800" -> WeatherTime.EVENING;
                                    default -> WeatherTime.UNKNOW;
                                };
                                Weather weather = new Weather(
                                        null,
                                        period,
                                        hourly.getTempC(),
                                        hourly.getWeatherDesc().get(0).getValue(),
                                        hourly.getWindspeedKmph() + " km/h (" + hourly.getWinddir16Point() + ")",
                                        hourly.getChanceofrain() + "%",
                                        hourly.getHumidity() + "%",
                                        LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE),
                                        latitude,
                                        longitude,
                                        communes
                                );
                                weatherSet.add(weather);
                            }
                        });
                    });
                    if (!weatherSet.isEmpty()){
                        weatherRepository.saveAll(weatherSet);
                    }
                }
            }
            System.err.println("DONE");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Set<WeatherRecord> getWeathers(LocalDate today, WeatherTime weatherTime, String dep){
        Utilisateur utilisateur = utilsService.getUser();
        RolesName role = getPriorityRole(utilisateur.getGroupe());
        switch (role) {
            case LOCAL_AUTHORITY: {
                Prefet prefet = prefetRepo.findAllByUtilisateur(utilisateur).orElseThrow(()->new RuntimeException("Vous ne dispose pas des droit requis"));
                List<Communes> communes = communeRepo.findAllByDepartement_Id(prefet.getDepartement().getId());
                Set<WeatherRecord> weatherRecordSet = new HashSet<>();
                communes.forEach(commune ->{
                    List<Weather>  weathers = weatherRepository.findAllByCommunes_IdAndDateAndWeatherTime(commune.getId(), today, weatherTime);
                    weatherRecordSet.addAll(weathers.stream().map(weatherMapper::toDTO).collect(Collectors.toSet()));
                });
                return weatherRecordSet;
            }
            case MAIRE: {
                Maire maire = maireRepo.findAllByUtilisateur(utilisateur).orElseThrow(()->new RuntimeException("Vous ne dispose pas des droit requis"));
                Communes commune = maire.getCommune();
                List<Weather>  weathers = weatherRepository.findAllByCommunes_IdAndDateAndWeatherTime(commune.getId(), today, weatherTime);
                return weathers.stream().map(weatherMapper::toDTO).collect(Collectors.toSet());
            }
            case SUPERADMIN, ADMIN, ROOT: {
                if(dep==null)
                    throw new RuntimeException("Selectionnez un departement");
                List<Communes> communes = communeRepo.findAllByDepartement_Id(UUID.fromString(dep));

                Set<WeatherRecord> weatherRecordSet = new HashSet<>();
                communes.forEach(commune ->{
                    List<WeatherRecord>  weathers = weatherRepository.findAllByCommunes_IdAndDateAndWeatherTime(commune.getId(), today, weatherTime)
                            .stream()
                            .collect(Collectors.toMap(
                                    w -> w.getCommunes().getId(), // clé : id de la commune
                                    weatherMapper::toDTO,         // valeur : DTO
                                    (existing, replacement) -> existing // en cas de doublon, garder le premier
                            ))
                            .values()
                            .stream().collect(Collectors.toList());
                    weatherRecordSet.addAll(weathers);
                });
                return weatherRecordSet;
            }
            default:
                throw new RuntimeException("Vous n'etes pas authorise.");
        }

    }

    public Set<WeatherRecord> getWeathersPublic(LocalDate today, WeatherTime weatherTime, UUID dep){
        Departement departement = departementsRepo.findById(dep).orElseThrow(()->new RuntimeException("Departement non trouve"));
        List<Communes> communes = communeRepo.findAllByDepartement_Id(departement.getId());
        Set<WeatherRecord> weatherRecordSet = new HashSet<>();
        communes.forEach(commune ->{
            List<Weather>  weathers = weatherRepository.findAllByCommunes_IdAndDateAndWeatherTime(commune.getId(), today, weatherTime);
            weatherRecordSet.addAll(weathers.stream().map(weatherMapper::toDTO).collect(Collectors.toSet()));
        });
        return weatherRecordSet;
    }

    private RolesName getPriorityRole(Groupes groupes){
        if(groupes==null)
            throw new RuntimeException("Vous n'etes pas authorise.");
        if(groupes.getRoles()==null)
            throw new RuntimeException("Vous n'etes pas authorise.");
        if(groupes.getRoles().isEmpty())
            throw new RuntimeException("Vous n'etes pas authorise.");
        List<Roles> rolesList = groupes.getRoles().stream().toList();
        List<RolesName> rolesNames = rolesList.stream().map(roles -> RolesName.valueOf(roles.getNom())).toList();
        if(rolesNames.isEmpty())
            throw new RuntimeException("Vous n'etes pas authorise.");
        if(rolesNames.contains(RolesName.SUPERADMIN))
            return RolesName.SUPERADMIN;
        if(rolesNames.contains(RolesName.ROOT))
            return RolesName.ROOT;
        if(rolesNames.contains(RolesName.ADMIN))
            return RolesName.ADMIN;
        if(rolesNames.contains(RolesName.LOCAL_AUTHORITY))
            return RolesName.LOCAL_AUTHORITY;
        if(rolesNames.contains(RolesName.MAIRE))
            return RolesName.MAIRE;
        if(rolesNames.contains(RolesName.CCPR_COMMITTEE))
            return RolesName.CCPR_COMMITTEE;
        if(rolesNames.contains(RolesName.COMMUNITY_COMMITTEE))
            return RolesName.COMMUNITY_COMMITTEE;
        else
            throw new RuntimeException("Vous n'etes pas authorise.");
    }

}
