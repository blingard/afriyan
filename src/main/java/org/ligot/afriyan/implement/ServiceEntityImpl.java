package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.ServiceDTO;
import org.ligot.afriyan.mapper.ServiceMapper;
import org.ligot.afriyan.repository.IServiceEntityRepository;
import org.ligot.afriyan.service.IServiceEntity;
import org.ligot.afriyan.entities.ServiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ServiceEntityImpl implements IServiceEntity {
    private final ServiceMapper mapper;
    private final IServiceEntityRepository repository;
    private final int PAGE_SIZE = 15;

    public ServiceEntityImpl(ServiceMapper mapper, IServiceEntityRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public ServiceDTO findById(Long id) throws Exception {
        ServiceEntity serviceEntity = repository.findById(id).orElse(null);
        if(serviceEntity == null){
            throw new Exception("Le Service que vous souhaitez modifier n'existes pas");
        }
        return mapper.toDTO(serviceEntity);
    }

    @Override
    public ServiceDTO save(ServiceDTO serviceDto) throws Exception {
        return mapper.toDTO(repository.save(mapper.create(serviceDto)));
    }

    @Override
    public Page<ServiceDTO> list(int page) throws Exception {
        Page<ServiceEntity> pages = repository.findAll(PageRequest.of(page,PAGE_SIZE));
        return new PageImpl<>(pages.map(mapper::toDTO).toList(),PageRequest.of(page,PAGE_SIZE),pages.getTotalElements());
    }

    @Override
    public ServiceDTO update(ServiceDTO serviceDto, Long id) throws Exception {
        ServiceEntity serviceEntity = repository.findById(id).orElse(null);
        if(serviceEntity == null){
            throw new Exception("Le Service que vous souhaitez modifier n'existes pas");
        }
        serviceDto.setId(id);
        return mapper.toDTO(repository.save(mapper.create(serviceDto)));
    }

    @Override
    public void delete(Long id) throws Exception {
        repository.deleteById(id);
    }
}
