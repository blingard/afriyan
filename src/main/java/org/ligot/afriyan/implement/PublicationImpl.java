package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.PublicationsDTO;
import org.ligot.afriyan.entities.Publications;
import org.ligot.afriyan.mapper.PublicationMapper;
import org.ligot.afriyan.repository.IPublicationRepository;
import org.ligot.afriyan.service.IPublications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PublicationImpl implements IPublications {

    private final int PAGE_SIZE = 15;
    private final PublicationMapper mapper;
    private final IPublicationRepository repository;

    public PublicationImpl(PublicationMapper mapper, IPublicationRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public PublicationsDTO findById(Long id) throws Exception {
        Publications cublication = repository.findById(id).orElse(null);
        if(cublication == null){
            throw new Exception("Le Publication que vous souhaitez modifier n'existes pas");
        }
        return mapper.toDTO(cublication);
    }

    @Override
    public PublicationsDTO save(PublicationsDTO publicationDto) throws Exception{
        return mapper.toDTO(repository.save(mapper.create(publicationDto)));
    }

    @Override
    public Page<PublicationsDTO> list(int page) {
        Page<Publications> pages = repository.findAll(PageRequest.of(page,PAGE_SIZE));
        return new PageImpl<>(pages.map(mapper::toDTO).toList(),PageRequest.of(page,PAGE_SIZE),pages.getTotalElements());
    }

    @Override
    public PublicationsDTO update(PublicationsDTO cublicationDto, Long id) throws Exception {
        Publications Publication = repository.findById(id).orElse(null);
        if (Publication == null){
            throw new Exception("Le Publicationq que vous souhaitez modifier n'existes pas");
        }

        return mapper.toDTO(repository.saveAndFlush(mapper.create(cublicationDto)));
    }

    @Override
    public void delete(Long id) throws Exception{
        repository.deleteById(id);
    }
}
