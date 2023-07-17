package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.ValeursDTO;
import org.ligot.afriyan.entities.Valeurs;
import org.ligot.afriyan.mapper.ValeursMapper;
import org.ligot.afriyan.repository.IValeursRepository;
import org.ligot.afriyan.service.IValeurs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ValeursImpl implements IValeurs {
    private final ValeursMapper mapper;
    private final IValeursRepository repository;

    public ValeursImpl(ValeursMapper mapper, IValeursRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public ValeursDTO save(ValeursDTO valeursDTO) {
        return mapper.toDTO(repository.save(mapper.create(valeursDTO)));
    }

    @Override
    public List<ValeursDTO> getList() {
        return repository.findAllByStatusTrue().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public ValeursDTO findById(Long id) throws Exception {
        Valeurs valeurs = repository.findById(id).orElse(null);
        if(valeurs == null)
            throw new Exception("Valeurs not found");
        return mapper.toDTO(valeurs);
    }

    @Override
    public Page<ValeursDTO> getPage(int lenght) {
        if(lenght<0)
            lenght = 0;
        Page<Valeurs> page = repository.findAll(PageRequest.of(lenght,15));
        return new PageImpl<>(
                page.getContent().stream().map(mapper::toDTO).collect(Collectors.toList()),
                PageRequest.of(lenght, 15),
                page.getSize()
        );
    }

    @Override
    public void update(ValeursDTO valeursDTO, Long id) {

    }

    @Override
    public void delete(Long id) {
        Valeurs valeurs = repository.findById(id).orElse(null);
        if(valeurs != null)
            repository.delete(valeurs);
    }
}