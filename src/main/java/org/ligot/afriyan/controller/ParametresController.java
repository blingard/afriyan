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
    public ResponseEntity<PageDTO<ParametresDto>> listByPage(@RequestParam(name = "page", defaultValue = "0")int page) throws Exception{
        return ResponseEntity.ok(service.findAllByPage(page));
    }

    @GetMapping("find-by-id/{id}")
    public ParametresDto findById(@PathVariable Long id) throws Exception {
        return service.findById(id);
    }
    @GetMapping("/{param}")
    public ResponseEntity<PageDTO<ParametresDto>> find(@PathVariable String param, @RequestParam(name = "page", defaultValue = "0")int page){
        ParamTypeEnum paramTypeEnum = ParamTypeEnum.valueOf(param);
        return ResponseEntity.ok(service.find(paramTypeEnum, page));
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


    @GetMapping("get/connect")
    public Long findConnect() throws Exception {
        return service.visiteurs();
    }
    @GetMapping("get/links")
    public Map<String, ParametresDto> findAllLinks(){
        return service.findAllLinks();
    }

    @PostMapping
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public void save(@RequestBody ParametresDto parametresDto) throws Exception {
        service.save(parametresDto);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody ParametresDto parametresDto, @PathVariable Long id) throws Exception {
        service.update(parametresDto, id);
    }

    @PutMapping("active/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT"})
    public void activeOrDesable(@PathVariable Long id) throws Exception {
        service.desable(id);
    }
}
