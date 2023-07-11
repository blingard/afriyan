package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.ServiceDto;
import org.ligot.afriyan.mapper.ServiceMapper;
import org.ligot.afriyan.repository.IServiceEntityRepository;
import org.ligot.afriyan.service.IServiceEntity;
import org.ligot.afriyan.entities.ServiceEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiceEntityImpl implements IServiceEntity {
    private final ServiceMapper mapper;
    private final IServiceEntityRepository repository;

    public ServiceEntityImpl(ServiceMapper mapper, IServiceEntityRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public ServiceEntity saveService(ServiceDto serviceDto) {
        ServiceEntity serviceEntity = mapper.create(serviceDto);
        return repository.save(serviceEntity);
    }

    @Override
    public List<ServiceDto> listService() {
        List<ServiceEntity> serviceEntitys = repository.findAll();

        List<ServiceDto> serviceDtos = null;
        serviceDtos.addAll(serviceEntitys.stream().map(serviceEntity -> {
                    ServiceDto serviceDto = mapper.toDTO(serviceEntity);
                    return serviceDto;
                }).collect(Collectors.toList()));
        return serviceDtos;
    }

    @Override
    public ServiceEntity updateService(ServiceDto serviceDto, long id) {
        ServiceEntity serviceEntity = repository.getById(id);
        serviceEntity = mapper.create(serviceDto);
        return repository.saveAndFlush(serviceEntity);
    }

    @Override
    public void deleteService(long id) {
        repository.deleteById(id);
    }
}
