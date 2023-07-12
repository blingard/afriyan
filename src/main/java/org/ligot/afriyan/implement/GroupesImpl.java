package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.GroupesDTO;
import org.ligot.afriyan.Dto.RolesDTO;
import org.ligot.afriyan.entities.Groupes;
import org.ligot.afriyan.mapper.GroupesMapper;
import org.ligot.afriyan.repository.IGroupesRepository;
import org.ligot.afriyan.service.IGroupes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Transactional
public class GroupesImpl implements IGroupes {
    private final GroupesMapper mapper;

    private IGroupes iGroupes;
    private final IGroupesRepository repository;
    private final int PAGE_SIZE = 15;

    public GroupesImpl(GroupesMapper mapper, IGroupesRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public GroupesDTO findById(Long id) throws Exception {
        Groupes groupes = repository.findById(id).orElse(null);
        if(groupes == null){
            throw new Exception("Le Groupes que vous souhaitez modifier n'existes pas");
        }
        return mapper.toDTO(groupes);
    }

    @Override
    public GroupesDTO save(GroupesDTO groupesDTO) throws Exception {
        return mapper.toDTO(repository.save(mapper.create(groupesDTO)));
    }

    @Override
    public Page<GroupesDTO> list(int page) throws Exception {
        Page<Groupes> pages = repository.findAll(PageRequest.of(page,PAGE_SIZE));
        return new PageImpl<>(pages.map(mapper::toDTO).toList(),PageRequest.of(page,PAGE_SIZE),pages.getTotalElements());
    }

    @Override
    public GroupesDTO update(GroupesDTO groupesDTO, Long id) throws Exception {
        Groupes groupes = repository.findById(id).orElse(null);
        if(groupes == null){
            throw new Exception("Le Groupes que vous souhaitez modifier n'existes pas");
        }
        groupesDTO.setId(id);
        return mapper.toDTO(repository.save(mapper.create(groupesDTO)));
    }

    @Override
    public void delete(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public GroupesDTO addRoles(GroupesDTO groupesDTO, Long id) throws Exception {
        Groupes groupes = repository.findById(id).orElse(null);
        if (groupes.getRoles().isEmpty()){
            groupes.setRoles(mapper.create(groupesDTO).getRoles());
            return mapper.toDTO(repository.saveAndFlush(groupes));

        }
        return iGroupes.update(groupesDTO, id);
    }
}
