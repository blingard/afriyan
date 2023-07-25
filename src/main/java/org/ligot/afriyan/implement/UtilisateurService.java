package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.GroupesDTO;
import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.entities.Groupes;
import org.ligot.afriyan.entities.Status;
import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.init.RolesName;
import org.ligot.afriyan.mapper.UtilisateurMapper;
import org.ligot.afriyan.repository.IUtilisateurRepository;
import org.ligot.afriyan.service.IGroupes;
import org.ligot.afriyan.service.IUtilisateur;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.ligot.afriyan.implement.Utils.genCode;

@Service
@Transactional
public class UtilisateurService implements IUtilisateur {

    @Value("{default.pwd.user}")
    private String userPWD;
    @Value("{default.pwd.client}")
    private String clientPWD;
    private final IUtilisateurRepository repository;
    private final IGroupes groupesService;
    private final PasswordEncoder passwordEncoder;
    private final UtilisateurMapper mapper;

    public UtilisateurService(IUtilisateurRepository repository, IGroupes groupesService, PasswordEncoder passwordEncoder, UtilisateurMapper mapper) {
        this.repository = repository;
        this.groupesService = groupesService;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @Override
    public UtilisateurDTO findById(Long id) throws Exception {
        Utilisateur utilisateur = repository.findById(id).orElse(null);
        if(utilisateur == null)
            throw new Exception("User with id = "+id+" don't exist");
        return mapper.toDTO(utilisateur);
    }

    @Override
    public UtilisateurDTO save(UtilisateurDTO utilisateurDTO, Long idGroupe) throws Exception {
        GroupesDTO groupe = groupesService.findById(idGroupe);
        boolean codeIsCreate = false;
        String code = "";
        while(!codeIsCreate){
            code = genCode("US",8);
            if(!repository.findByCode(code).isPresent())
                codeIsCreate = true;
        }
        utilisateurDTO.setId(null);
        utilisateurDTO.setCode(code);
        utilisateurDTO.setGroupe(groupe);
        utilisateurDTO.setPwd(passwordEncoder.encode(userPWD+code));
        Utilisateur utilisateur = mapper.create(utilisateurDTO);
        System.err.println(utilisateur.getPwd());
        return mapper.toDTO(repository.save(mapper.create(utilisateurDTO)));
    }

    @Override
    public UtilisateurDTO register(UtilisateurDTO utilisateurDTO) throws Exception {
        GroupesDTO groupe = groupesService.findByName(RolesName.USER.toString());
        boolean codeIsCreate = false;
        String code = "";
        while(!codeIsCreate){
            code = genCode("US",8);
            if(!repository.findByCode(code).isPresent())
                codeIsCreate = true;
        }
        utilisateurDTO.setId(null);
        utilisateurDTO.setCode(code);
        utilisateurDTO.setGroupe(groupe);
        Utilisateur utilisateur = repository.save(mapper.create(utilisateurDTO));
        return mapper.toDTO(utilisateur);
    }

    @Override
    public Page<UtilisateurDTO> list(int page) throws Exception {
        Page<Utilisateur> utilisateurs = repository.findAll(PageRequest.of(page, 15));
        return  new PageImpl<>(
                utilisateurs.stream().map(mapper::toDTO).toList(),
                PageRequest.of(page, 15),
                utilisateurs.getContent().size());
    }

    @Override
    public Page<UtilisateurDTO> list(int page, Long idGroup) throws Exception {
        Page<Utilisateur> utilisateurs = repository.findByGroupe(new Groupes(idGroup),PageRequest.of(page, 15));
        return  new PageImpl<>(
                utilisateurs.stream().map(mapper::toDTO).toList(),
                PageRequest.of(page, 15),
                utilisateurs.getContent().size());
    }

    @Override
    public UtilisateurDTO update(UtilisateurDTO utilisateurDTO, Long id) throws Exception {
        return null;
    }

    @Override
    public void disableUtilisateur(Long id) throws Exception {
        Utilisateur utilisateur = repository.findById(id).orElse(null);
        if(utilisateur == null)
            throw new Exception("User with id "+id+" don't exist");
        if(utilisateur.getStatus()== Status.ACTIVE)
            utilisateur.setStatus(Status.INACTIVE);
        else
            utilisateur.setStatus(Status.ACTIVE);
        repository.save(utilisateur);

    }

    @Override
    public UtilisateurDTO findByName(String nom) throws Exception {
        Utilisateur utilisateur = repository.findByNom(nom);
        if(utilisateur == null)
            throw new Exception("User with id = "+nom+" don't exist");
        return mapper.toDTO(utilisateur);
    }
}
