package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.MissionsDTO;
import org.ligot.afriyan.entities.Missions;
import org.ligot.afriyan.mapper.MissionsMapper;
import org.ligot.afriyan.repository.IMissionsRepository;
import org.ligot.afriyan.service.IMissions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MissionsImpl implements IMissions {
    private final IMissionsRepository repository;
    private final MissionsMapper mapper;

    public MissionsImpl(IMissionsRepository repository, MissionsMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public MissionsDTO save(MissionsDTO valeursDTO) {
        return mapper.toDTO(repository.save(mapper.create(valeursDTO)));
    }

    @Override
    public List<MissionsDTO> getList() {
        return repository
                .findAllByStatusTrue()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MissionsDTO findById(Long id) throws Exception {
        Missions missions = repository.findById(id).orElse(null);
        if(missions == null)
            throw new Exception("Valeurs not found");
        return mapper.toDTO(missions);
    }

    @Override
    public Page<MissionsDTO> getPage(int lenght) {
        if(lenght<0)
            lenght = 0;
        Page<Missions> page = repository.findAll(PageRequest.of(lenght,15));
        return new PageImpl<>(
                page.getContent().stream().map(mapper::toDTO).collect(Collectors.toList()),
                PageRequest.of(lenght, 15),
                page.getSize()
        );
    }

    @Override
    public void update(MissionsDTO valeursDTO, Long id) {

    }

    @Override
    public void delete(Long id) {
        Missions missions = repository.findById(id).orElse(null);
        if(missions != null)
            repository.delete(missions);

    }
}
