package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.GroupesDTO;
import org.ligot.afriyan.Dto.RolesDTO;
import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.entities.Groupes;
import org.ligot.afriyan.entities.Roles;
import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.mapper.GroupesMapper;
import org.ligot.afriyan.repository.IGroupesRepository;
import org.ligot.afriyan.repository.IRolesRepository;
import org.ligot.afriyan.repository.IUtilisateurRepository;
import org.ligot.afriyan.service.IGroupes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
@Service
@Transactional
public class GroupesImpl implements IGroupes {
    private final GroupesMapper mapper;
    private final IGroupesRepository repository;
    private final IRolesRepository iRolesRepository;
    private final IUtilisateurRepository iUtilisateurRepository;
    private final int PAGE_SIZE = 15;

    public GroupesImpl(GroupesMapper mapper, IGroupesRepository repository, IRolesRepository iRolesRepository, IUtilisateurRepository iUtilisateurRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.iRolesRepository = iRolesRepository;
        this.iUtilisateurRepository = iUtilisateurRepository;
    }

    @Override
    public GroupesDTO findById(Long id) throws Exception {
        Groupes groupes = repository.findById(id).orElse(null);
        if(groupes == null){
            throw new Exception("Group with id "+id+" don't exist");
        }
        return mapper.toDTO(groupes);
    }

    @Override
    public GroupesDTO findByName(String name) throws Exception {
        Groupes groupes = repository.findByName(name).orElse(null);
        if(groupes == null){
            throw new Exception("Group with name "+name+" don't exist");
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
    public GroupesDTO addRoles(Set<RolesDTO> rolesDTOs, Long id) throws Exception {
        Groupes groupes = repository.findById(id).orElse(null);
        if(groupes == null){
            throw new Exception("Le Groupes que vous souhaitez modifier n'existes pas");
        }
        List<Roles> roles = new ArrayList<>();
        Roles role = new Roles();
        Iterator<RolesDTO> iterable = rolesDTOs.iterator();
        while (iterable.hasNext()){
            role = iRolesRepository.findById(iterable.next().getId()).orElse(null);
            if(role != null){
                roles.add(role);
            }
        }
        roles.forEach(roles1 -> {
            if(!groupes.getRoles().contains(roles1)){
                groupes.getRoles().add(roles1);
            }
        });
        return mapper.toDTO(repository.save(groupes));
    }

    @Override
    public void removeRoles(Set<RolesDTO> rolesDTOs, Long id) throws Exception {
        Groupes groupes = repository.findById(id).orElse(null);
        if(groupes == null){
            throw new Exception("Le Groupes que vous souhaitez modifier n'existes pas");
        }
        List<Roles> roles = new ArrayList<>();
        Roles role = new Roles();
        Iterator<RolesDTO> iterable = rolesDTOs.iterator();
        while (iterable.hasNext()){
            role = iRolesRepository.findById(iterable.next().getId()).orElse(null);
            if(role != null){
                roles.add(role);
            }
        }
        roles.forEach(roles1 -> {
            if(groupes.getRoles().contains(roles1)){
                groupes.getRoles().remove(roles1);
            }
        });
        repository.save(groupes);
    }

    @Override
    public GroupesDTO addUsers(Set<UtilisateurDTO> utilisateurDTOS, Long id) throws Exception {
        Groupes groupes = repository.findById(id).orElse(null);
        if(groupes == null){
            throw new Exception("Le Groupes que vous souhaitez modifier n'existes pas");
        }
        List<Utilisateur> utilisateurs = new ArrayList<>();
        Utilisateur user = new Utilisateur();
        Iterator<UtilisateurDTO> iterable = utilisateurDTOS.iterator();
        while (iterable.hasNext()){
            user = iUtilisateurRepository.findById(iterable.next().getId()).orElse(null);
            if(user != null){
                utilisateurs.add(user);
            }
        }
        utilisateurs.forEach(utilisateur -> {
            if(!groupes.getUtilisateurs().contains(utilisateur)){
                groupes.getUtilisateurs().add(utilisateur);
            }
        });
        return mapper.toDTO(repository.save(groupes));
    }

    @Override
    public void removeUsers(Set<UtilisateurDTO> utilisateurDTOS, Long id) throws Exception {
        Groupes groupes = repository.findById(id).orElse(null);
        if(groupes == null){
            throw new Exception("Le Groupes que vous souhaitez modifier n'existes pas");
        }
        List<Utilisateur> utilisateurs = new ArrayList<>();
        Utilisateur user = new Utilisateur();
        Iterator<UtilisateurDTO> iterable = utilisateurDTOS.iterator();
        while (iterable.hasNext()){
            user = iUtilisateurRepository.findById(iterable.next().getId()).orElse(null);
            if(user != null){
                utilisateurs.add(user);
            }
        }
        utilisateurs.forEach(utilisateur -> {
            if(groupes.getUtilisateurs().contains(utilisateur)){
                groupes.getUtilisateurs().remove(utilisateur);
            }
        });
        repository.save(groupes);
    }
}
