package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.OurWorksDTO;
import org.ligot.afriyan.entities.OurWorks;
import org.ligot.afriyan.entities.OurWorksType;
import org.ligot.afriyan.mapper.OurWorksMapper;
import org.ligot.afriyan.repository.IOurWorksRepository;
import org.ligot.afriyan.service.IOurWorks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OurWorksImpl implements IOurWorks {
    private final IOurWorksRepository repository;
    private final OurWorksMapper mapper;

    public OurWorksImpl(IOurWorksRepository repository, OurWorksMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public OurWorksDTO save(OurWorksDTO valeursDTO) {
        return mapper.toDTO(repository.save(mapper.create(valeursDTO)));
    }

    @Override
    public List<OurWorksDTO> getList(OurWorksType ourWorksType) {
        return repository.findAllByStatusTrueAndAndOurWorksType(ourWorksType).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public OurWorksDTO findById(Long id) throws Exception {
        OurWorks ourWorks = repository.findById(id).orElse(null);
        if(ourWorks == null)
            throw new Exception("ourWorks not found");
        return mapper.toDTO(ourWorks);
    }

    @Override
    public Page<OurWorksDTO> getPage(int lenght) {
        if(lenght<0)
            lenght = 0;
        Page<OurWorks> page = repository.findAll(PageRequest.of(lenght,15));
        return new PageImpl<>(
                page.getContent().stream().map(mapper::toDTO).collect(Collectors.toList()),
                PageRequest.of(lenght, 15),
                page.getSize()
        );
    }

    @Override
    public void update(OurWorksDTO ourWorksDTO, Long id) {

    }

    @Override
    public void delete(Long id) {
        OurWorks ourWorks = repository.findById(id).orElse(null);
        if(ourWorks != null)
            repository.delete(ourWorks);
    }
}
