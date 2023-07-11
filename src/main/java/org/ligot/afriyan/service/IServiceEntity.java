package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.ServiceDto;
import org.ligot.afriyan.entities.ServiceEntity;

import java.util.List;

public interface IServiceEntity {

    ServiceEntity saveService(ServiceDto serviceDto);
    List<ServiceDto> listService();
    ServiceEntity updateService(ServiceDto serviceDto, long id);
    void deleteService(long id);

}
