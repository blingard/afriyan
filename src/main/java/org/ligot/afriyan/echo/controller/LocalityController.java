package org.ligot.afriyan.echo.controller;

import org.ligot.afriyan.echo.AccentUtils;
import org.ligot.afriyan.echo.dto.*;
import org.ligot.afriyan.echo.mapper.CommuneMapper;
import org.ligot.afriyan.echo.mapper.DepartementMapper;
import org.ligot.afriyan.echo.mapper.LocationMapper;
import org.ligot.afriyan.echo.mapper.RegionMapper;
import org.ligot.afriyan.echo.repo.CommuneRepo;
import org.ligot.afriyan.echo.repo.DepartementsRepo;
import org.ligot.afriyan.echo.repo.LocalityRepo;
import org.ligot.afriyan.echo.repo.RegionsRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class LocalityController {
    private final RegionsRepo regionsRepo;
    private final RegionMapper regionMapper;
    private final DepartementsRepo departementsRepo;
    private final DepartementMapper departementMapper;
    private final CommuneRepo communeRepo;
    private final CommuneMapper communeMapper;
    private final LocalityRepo localityRepo;
    private final LocationMapper locationMapper;

    public LocalityController(RegionsRepo regionsRepo, RegionMapper regionMapper, DepartementsRepo departementsRepo, DepartementMapper departementMapper, CommuneRepo communeRepo, CommuneMapper communeMapper, LocalityRepo localityRepo, LocationMapper locationMapper) {
        this.regionsRepo = regionsRepo;
        this.regionMapper = regionMapper;
        this.departementsRepo = departementsRepo;
        this.departementMapper = departementMapper;
        this.communeRepo = communeRepo;
        this.communeMapper = communeMapper;
        this.localityRepo = localityRepo;
        this.locationMapper = locationMapper;
    }


    @GetMapping("public/locality/list/reg")
    public Set<RegionDTO> listAllRegion() throws Exception {
        return regionsRepo.findAll().stream().map(regionMapper::toDTO).collect(Collectors.toSet());
    }


    @GetMapping("public/locality/list/dep")
    public Set<DepartementDTO> listAllDep() throws Exception {
        return departementsRepo.findAll().stream().map(departementMapper::toDTO).collect(Collectors.toSet());
    }


    @GetMapping("public/locality/list/dep/{id}")
    public Set<DepartementDTO> listAllDepById(@PathVariable UUID id) throws Exception {
        return departementsRepo.findDepartementsByRegion_Id(id).stream().map(departementMapper::toDTO).collect(Collectors.toSet());
    }


    @GetMapping("public/locality/list/com")
    public Set<CommunesDTO> listAllCom() throws Exception {
        return communeRepo.findAll().stream().map(communeMapper::toDTO).collect(Collectors.toSet());
    }


    @GetMapping("public/locality/list/com/{id}")
    public Set<CommunesDTO> listAllComById(@PathVariable UUID id) throws Exception {
        return communeRepo.findAllByDepartement_Id(id).stream().map(communeMapper::toDTO).collect(Collectors.toSet());
    }


    @GetMapping("public/locality/list/loc")
    public Set<LocalitiesDTO> listAllLoc() throws Exception {
        return localityRepo.findAll().stream().map(locationMapper::toDTO).collect(Collectors.toSet());
    }

    @GetMapping("public/locality/search/loc")
    public List<LocalitiesDTO> searchAllLoc(@RequestParam(name = "name") String name) throws Exception {
        return localityRepo.findByNameStartingWithIgnoreCase(AccentUtils.normalize(name)).stream().map(locationMapper::toDTO).toList();
    }

    @GetMapping("public/locality/search/com")
    public List<CommunesDTO> searchAllCom(@RequestParam(name = "name") String name) throws Exception {
        return communeRepo.findByNameStartingWithIgnoreCase(AccentUtils.normalize(name)).stream().map(communeMapper::toDTO).toList();
    }

}
