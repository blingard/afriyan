package org.ligot.afriyan.controller;

import jakarta.annotation.security.RolesAllowed;
import org.ligot.afriyan.Dto.ServiceDTO;
import org.ligot.afriyan.service.IServiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class ServiceController {

    private final IServiceEntity serviceEntity;

    public ServiceController(IServiceEntity serviceEntity) {
        this.serviceEntity = serviceEntity;
    }

    @PostMapping(value = "api/service/save")
    @RolesAllowed(value = {"CREATE_SERVICE"})
    public ServiceDTO saveService(@RequestBody ServiceDTO serviceEntityDto) throws Exception {
        return serviceEntity.save(serviceEntityDto);
    }

    @PutMapping(value = "api/service/update/{id}")
    @RolesAllowed(value = {"UPDATE_SERVICE"})
    public ServiceDTO updateService(@RequestBody ServiceDTO serviceEntityDto, @PathVariable Long id) throws Exception {
        return serviceEntity.update(serviceEntityDto, id);
    }

    @GetMapping(value = "api/service/list/{page}")
    @RolesAllowed(value = {"GET_SERVICE"})
    public Page<ServiceDTO> listService(@PathVariable  int page) throws Exception {
        return serviceEntity.list(page);
    }

    @GetMapping(value = "api/service/list-by-cp-id/{id}")
    public List<ServiceDTO> listServiceCP(@PathVariable  Long id) throws Exception {
        return serviceEntity.listServiceCP(id);
    }

    @DeleteMapping(value = "api/service/delete/{id}")
    @RolesAllowed(value = {"DELETE_SERVICE"})
    public void deleteService (@PathVariable long id) throws Exception{
        serviceEntity.delete(id);
    }

    @GetMapping(value = "api/service/getById/{id}")
    public ServiceDTO listById(@PathVariable Long id) throws Exception {
        return serviceEntity.findById(id);
    }

    @GetMapping(value = "api/service/getById/one/{id}")
    public ServiceDTO listByIdActive(@PathVariable Long id) throws Exception {
        return serviceEntity.findByIdUser(id);
    }

    @GetMapping(value = "api/service/getById/user/{id}")
    public Set<ServiceDTO> listByIdCPUser(@PathVariable Long id) throws Exception {
        return serviceEntity.findListByIdUser(id);
    }

    @GetMapping("api/service")
    public List<ServiceDTO> listAll() throws Exception {
        return serviceEntity.listAll();
    }


}
