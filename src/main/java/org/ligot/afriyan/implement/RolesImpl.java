package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.RolesDTO;
import org.ligot.afriyan.entities.Roles;
import org.ligot.afriyan.mapper.RolesMapper;
import org.ligot.afriyan.repository.IRolesRepository;
import org.ligot.afriyan.service.IRoles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RolesImpl implements IRoles {
    private final RolesMapper mapper;
    private final IRolesRepository repository;
    private final int PAGE_SIZE = 15;

    public RolesImpl(RolesMapper mapper, IRolesRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public RolesDTO findById(Long id) throws Exception {
        Roles roles = repository.findById(id).orElse(null);
        if(roles == null){
            throw new Exception("Le Roles que vous souhaitez modifier n'existes pas");
        }
        return mapper.toDTO(roles);
    }

    @Override
    public RolesDTO save(RolesDTO RolesDTO) throws Exception {
        return mapper.toDTO(repository.save(mapper.create(RolesDTO)));
    }

    @Override
    public Page<RolesDTO> list(int page) throws Exception {
        Page<Roles> pages = repository.findAll(PageRequest.of(page,PAGE_SIZE));
        return new PageImpl<>(pages.map(mapper::toDTO).toList(),PageRequest.of(page,PAGE_SIZE),pages.getTotalElements());
    }

    @Override
    public List<RolesDTO> list() throws Exception {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public RolesDTO update(RolesDTO rolesDTO, Long id) throws Exception {
        Roles roles = repository.findById(id).orElse(null);
        if(roles == null){
            throw new Exception("Le Roles que vous souhaitez modifier n'existes pas");
        }
        rolesDTO.setId(id);
        return mapper.toDTO(repository.save(mapper.create(rolesDTO)));
    }

    @Override
    public void delete(Long id) throws Exception {
        repository.deleteById(id);
    }
}
