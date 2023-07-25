package org.ligot.afriyan.controller;

import org.ligot.afriyan.Dto.ServiceDTO;
import org.ligot.afriyan.service.IServiceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/service")
public class ServiceController {

    @Autowired
    IServiceEntity serviceEntity;

    @PostMapping(value = "/save")
    ServiceDTO saveService(@RequestBody ServiceDTO serviceEntityDto) throws Exception {
        return serviceEntity.save(serviceEntityDto);
    }

    @PutMapping(value = "/update/{id}")
    ServiceDTO updateService(@RequestBody ServiceDTO serviceEntityDto, @PathVariable Long id) throws Exception {
        return serviceEntity.update(serviceEntityDto, id);
    }

    @GetMapping(value = "/list/{page}")
    Page<ServiceDTO> listService(@PathVariable  int page) throws Exception {
        return serviceEntity.list(page);
    }

    @DeleteMapping(value = "/delete/{id}")
    void deleteService (@PathVariable long id) throws Exception{
        serviceEntity.delete(id);
    }

    @GetMapping(value = "/getById/{id}")
    ServiceDTO listById(@PathVariable Long id) throws Exception {
        return serviceEntity.findById(id);
    }


}
