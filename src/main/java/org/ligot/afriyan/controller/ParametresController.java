package org.ligot.afriyan.controller;

import jakarta.annotation.security.RolesAllowed;
import org.ligot.afriyan.Dto.PageDTO;
import org.ligot.afriyan.Dto.ParametresDto;
import org.ligot.afriyan.entities.ParamTypeEnum;
import org.ligot.afriyan.service.IParametres;
import org.ligot.afriyan.sondage.dto.SondageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ParametresController {
    private final IParametres service;

    public ParametresController(IParametres service) {
        this.service = service;
    }
    @GetMapping("api/parametres/get/active")
    public List<ParametresDto> findAllActiveStat(){
        return service.findAllActive();
    }

    @GetMapping("/api/parametres")
    public ResponseEntity<PageDTO<ParametresDto>> listByPage(@RequestParam(name = "page", defaultValue = "0")int page) throws Exception{
        return ResponseEntity.ok(service.findAllByPage(page));
    }

    @GetMapping("api/parametres/find-by-id/{id}")
    public ParametresDto findById(@PathVariable Long id) throws Exception {
        return service.findById(id);
    }
    @GetMapping("api/parametres/{param}")
    public ResponseEntity<PageDTO<ParametresDto>> find(@PathVariable String param, @RequestParam(name = "page", defaultValue = "0")int page){
        ParamTypeEnum paramTypeEnum = ParamTypeEnum.valueOf(param);
        return ResponseEntity.ok(service.find(paramTypeEnum, page));
    }

    @GetMapping("api/parametres/get/call")
    public ParametresDto findCall(){
        return service.findCall();
    }

    @GetMapping("api/parametres/get/whatsapp")
    public ParametresDto findWhatsapp(){
        return service.findWhatsapp();
    }

    @GetMapping("api/parametres/get/sms")
    public ParametresDto findSms(){
        return service.findSms();
    }

    @GetMapping("api/parametres/get/youtube")
    public ParametresDto findYoutube(){
        return service.findYoutube();
    }

    @GetMapping("api/parametres/get/facebook")
    public ParametresDto findFacebook(){
        return service.findFacebook();
    }

    @GetMapping("api/parametres/get/tweeter")
    public ParametresDto findTweeter(){
        return service.findTweeter();
    }


    @GetMapping("api/parametres/get/connect")
    public Long findConnect() throws Exception {
        return service.visiteurs();
    }
    @GetMapping("public/api/parametres/get/links")
    public Map<String, ParametresDto> findAllLinks(){
        return service.findAllLinks();
    }


    @PutMapping("api/parametres/{id}")
    public void update(@RequestBody ParametresDto parametresDto, @PathVariable Long id) throws Exception {
        service.update(parametresDto, id);
    }

    @PutMapping("api/parametres/active/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    public void activeOrDesable(@PathVariable Long id) throws Exception {
        service.desable(id);
    }
}
