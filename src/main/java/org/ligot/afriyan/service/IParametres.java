package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.ParametresDto;
import org.ligot.afriyan.entities.ParamTypeEnum;

import java.util.List;
import java.util.Map;

public interface IParametres {
    void save(ParametresDto parametresDto) throws Exception;
    void update(ParametresDto parametresDto, Long id) throws Exception;
    void desable(Long id) throws Exception;
    List<ParametresDto> findAllActive();
    List<ParametresDto> findAll();
    ParametresDto findCall();
    ParametresDto findLocation();
    ParametresDto findSms();
    ParametresDto findWhatsapp();
    ParametresDto findFacebook();
    ParametresDto findTweeter();
    ParametresDto findYoutube();

    Map<String, ParametresDto> findAllLinks();

    List<ParametresDto> find(ParamTypeEnum paramTypeEnum);

    ParametresDto findById(Long id) throws Exception;

    Long visiteurs() throws Exception;
}
