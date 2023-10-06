package org.ligot.afriyan.implement;

import org.ligot.afriyan.Dto.MediatechDTO;
import org.ligot.afriyan.entities.Mediatech;
import org.ligot.afriyan.mapper.MediatechMapper;
import org.ligot.afriyan.repository.IMediatechRepository;
import org.ligot.afriyan.service.IMediatech;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediatechImpl implements IMediatech {

    private final MediatechMapper mapper;
    private final IMediatechRepository repository;

    public MediatechImpl(MediatechMapper mapper, IMediatechRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public List<MediatechDTO> findAll() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public List<MediatechDTO> findAllActive() {
        return repository.findAllByActiveTrue().stream().map(mapper::toDTO).toList();
    }

    @Override
    public void activeOrDesable(Long id) {
        Mediatech mediatech = repository.findById(id).orElse(null);
        if(mediatech != null){
            mediatech.setActive(!mediatech.isActive());
            repository.save(mediatech);
        }
    }
}
