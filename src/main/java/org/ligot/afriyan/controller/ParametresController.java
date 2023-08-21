package org.ligot.afriyan.controller;

import org.ligot.afriyan.Dto.ParametresDto;
import org.ligot.afriyan.entities.ParamTypeEnum;
import org.ligot.afriyan.service.IParametres;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/parametres")
public class ParametresController {
    private final IParametres service;

    public ParametresController(IParametres service) {
        this.service = service;
    }
    @GetMapping("get/active")
    public List<ParametresDto> findAllActiveStat(){
        return service.findAllActive();
    }

    @GetMapping
    public List<ParametresDto> findAll(){
        return service.findAll();
    }

    @GetMapping("find-by-id/{id}")
    public ParametresDto findById(@PathVariable Long id) throws Exception {
        return service.findById(id);
    }
    @GetMapping("/{param}")
    public List<ParametresDto> find(@PathVariable String param){
        ParamTypeEnum paramTypeEnum = ParamTypeEnum.valueOf(param);
        return service.find(paramTypeEnum);
    }

    @GetMapping("get/call")
    public ParametresDto findCall(){
        return service.findCall();
    }

    @GetMapping("get/whatsapp")
    public ParametresDto findWhatsapp(){
        return service.findWhatsapp();
    }

    @GetMapping("get/sms")
    public ParametresDto findSms(){
        return service.findSms();
    }

    @GetMapping("get/youtube")
    public ParametresDto findYoutube(){
        return service.findYoutube();
    }

    @GetMapping("get/facebook")
    public ParametresDto findFacebook(){
        return service.findFacebook();
    }

    @GetMapping("get/tweeter")
    public ParametresDto findTweeter(){
        return service.findTweeter();
    }


    @GetMapping("get/links")
    public Map<String, ParametresDto> findAllLinks(){
        return service.findAllLinks();
    }

    @PostMapping
    public void save(@RequestBody ParametresDto parametresDto) throws Exception {
        service.save(parametresDto);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody ParametresDto parametresDto, @PathVariable Long id) throws Exception {
        service.update(parametresDto, id);
    }

    @PutMapping("active/{id}")
    public void activeOrDesable(@PathVariable Long id) throws Exception {
        service.desable(id);
    }
}
