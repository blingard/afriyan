package org.ligot.afriyan.echo.service;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.PageDTO;
import org.ligot.afriyan.echo.AlertRiskTypeFake;
import org.ligot.afriyan.echo.dto.AlertRiskTypeDTO;
import org.ligot.afriyan.echo.entities.AlertRiskType;
import org.ligot.afriyan.echo.mapper.AlertRiskTypeMapper;
import org.ligot.afriyan.echo.repo.AlertRiskTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.ligot.afriyan.implement.Utils.genCode;
@Service
@Transactional
public class AlertRiskTypeService {

    private final AlertRiskTypeMapper mapper;
    private final AlertRiskTypeRepository repository;


    public AlertRiskTypeService(AlertRiskTypeMapper mapper, AlertRiskTypeRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }


    public AlertRiskType save(AlertRiskTypeDTO alertRiskTypeDTO) {
        boolean codeIsCreate = false;
        String code = "";
        while(!codeIsCreate){
            code = genCode("RS",8);
            if(!repository.findAllByCode(alertRiskTypeDTO.getCode()).isPresent())
                codeIsCreate = true;
        }
        alertRiskTypeDTO.setCode(code);
        AlertRiskType riskType = mapper.create(alertRiskTypeDTO);
        riskType.setId(null);
        riskType.setState(false);
        riskType.setIcon(alertRiskTypeDTO.getIcon());
        return repository.save(riskType);
    }

    public void init(){
//        for(AlertRiskTypeFake alertRiskTypeFake : AlertRiskTypeFake.values()){
//            try {
//                AlertRiskType riskType = save(new AlertRiskTypeDTO(null, alertRiskTypeFake.name(), "Alert de type "+alertRiskTypeFake.name(),
//                true, LocalDateTime.now(), null));
//                riskType.setState(true);
//                repository.save(riskType);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
    }
    public PageDTO<AlertRiskTypeDTO> getAllByPage(int page){
        Page<AlertRiskType> alertRiskTypes = repository.findAll(PageRequest.of(page, 10));
        return new PageDTO<AlertRiskTypeDTO>(
                new PageImpl<>(
                        alertRiskTypes.stream().map(mapper::toDTO).collect(Collectors.toList()),
                        alertRiskTypes.getPageable(),
                        alertRiskTypes.getTotalElements()
                )
        );
    }


    public AlertRiskTypeDTO findById(String id){
        UUID uuid = convertToUUID(id);
        AlertRiskType riskType = repository.findAllById(uuid).orElseThrow(()->new RuntimeException("Alert type n'existe pas"));
        return mapper.toDTO(riskType);
    }


    public AlertRiskTypeDTO findActiveById(String id){
        AlertRiskTypeDTO alertRiskTypeDTO = findById(id);
        if(Objects.equals(false,alertRiskTypeDTO.isState()))
            throw new RuntimeException("Le type d'alerte n'est pas actif");
        return alertRiskTypeDTO;
    }

    private UUID convertToUUID(String id){
        try{
            return UUID.fromString(id);
        }catch (Exception ex){
            throw new RuntimeException("L'identifiant saisi n'est pas valide");
        }
    }

    public List<AlertRiskTypeDTO> getAllActive(){
        return repository.findAllByState(true).stream().map(mapper::toDTO).toList();
    }

    public List<AlertRiskTypeDTO> getAllActivePublic(){
        return repository.findAllByState(true).stream().map(mapper::toDTO).toList();
    }

    public void active(String id){
        UUID uuid = convertToUUID(id);
        AlertRiskType riskType = repository.findAllById(uuid).orElseThrow(()->new RuntimeException("Alert type n'existe pas"));
        riskType.setState(!riskType.isState());
        repository.save(riskType);
    }

    public void update(AlertRiskTypeDTO alertRiskTypeDTO, String id) {
        UUID uuid = convertToUUID(id);
        AlertRiskType riskType = repository.findById(uuid).orElseThrow(()->new RuntimeException("Alert type non trouvet"));
        if(!Objects.equals(uuid, alertRiskTypeDTO.getId()))
            throw new RuntimeException("Information non concordante");
        mapper.update(alertRiskTypeDTO, riskType);
        riskType.setIcon(alertRiskTypeDTO.getIcon());
        repository.save(riskType);
    }
}
