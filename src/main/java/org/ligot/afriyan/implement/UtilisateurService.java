package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.ChangePwd;
import org.ligot.afriyan.Dto.GroupesDTO;
import org.ligot.afriyan.Dto.UserDetailsImpl;
import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.entities.Groupes;
import org.ligot.afriyan.entities.Status;
import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.init.RolesName;
import org.ligot.afriyan.mapper.UtilisateurMapper;
import org.ligot.afriyan.repository.IArticlesRepository;
import org.ligot.afriyan.repository.ICentrePartenaireRepository;
import org.ligot.afriyan.repository.IDenonciationRepository;
import org.ligot.afriyan.repository.IUtilisateurRepository;
import org.ligot.afriyan.service.IGroupes;
import org.ligot.afriyan.service.IUtilisateur;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.ligot.afriyan.implement.Utils.genCode;

@Service
@Transactional
public class UtilisateurService implements IUtilisateur {

    private final IUtilisateurRepository repository;
    private final ICentrePartenaireRepository iCentrePartenaireRepository;
    private final IArticlesRepository iArticlesRepository;
    private final IGroupes groupesService;
    private final IDenonciationRepository iDenonciationRepository;
    private final PasswordEncoder passwordEncoder;
    private final UtilisateurMapper mapper;

    public UtilisateurService(IUtilisateurRepository repository, ICentrePartenaireRepository iCentrePartenaireRepository, IArticlesRepository iArticlesRepository, IGroupes groupesService, IDenonciationRepository iDenonciationRepository, @Qualifier("passwordEncoder") PasswordEncoder passwordEncoder, UtilisateurMapper mapper) {
        this.repository = repository;
        this.iCentrePartenaireRepository = iCentrePartenaireRepository;
        this.iArticlesRepository = iArticlesRepository;
        this.groupesService = groupesService;
        this.iDenonciationRepository = iDenonciationRepository;
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
        System.err.println("passwordEncoder.encode(123456789)");
        System.err.println(passwordEncoder.encode("123456789"));
        System.err.println("passwordEncoder.encode(123456789)");
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
        //Send SMS to created user
        utilisateurDTO.setPwd(passwordEncoder.encode("123456789"));
        Utilisateur utilisateur = mapper.create(utilisateurDTO);
        utilisateur.setStatus(Status.ACTIVE);
        utilisateur.setIsFirstConnexion(true);
        return mapper.toDTO(repository.save(utilisateur));
    }

    @Override
    public UtilisateurDTO register(UtilisateurDTO utilisateurDTO) throws Exception {
        GroupesDTO groupe = groupesService.findByName(RolesName.USER.toString());
        boolean codeIsCreate = false;
        String code = "";
        while(!codeIsCreate){
            code = genCode("CL",8);
            if(!repository.findByCode(code).isPresent())
                codeIsCreate = true;
        }
        utilisateurDTO.setId(null);
        utilisateurDTO.setCode(code);
        utilisateurDTO.setGroupe(groupe);//
        utilisateurDTO.setStatus(Status.ACTIVE);
        utilisateurDTO.setPwd(passwordEncoder.encode(utilisateurDTO.getPwd()));
        Utilisateur utilisateur = mapper.create(utilisateurDTO);
        utilisateur.setStatus(Status.ACTIVE);
        utilisateur.setIsFirstConnexion(false);
        utilisateur = repository.save(mapper.create(utilisateurDTO));
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
    public List<UtilisateurDTO> list(Long groupId) throws Exception {
        if(groupId == 0)
            groupId = 1L;
        return repository.findByGroupe(new Groupes(groupId)).stream().map(mapper::toDTO).toList();
    }

    @Override
    public List<UtilisateurDTO> list() throws Exception {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public List<UtilisateurDTO> list(String role) throws Exception {
        groupesService.getByRole(role);
        return this.list();
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

    @Override
    public UtilisateurDTO findByLogin(String login) throws Exception {
        Utilisateur utilisateur = repository.findByEmail(login).orElse(null);
        if(utilisateur == null)
            throw new Exception("User with login = "+login+" not found");
        return mapper.toDTO(utilisateur);
    }

    @Override
    public UtilisateurDTO login(String login) throws Exception {
        Optional<Utilisateur> user = repository.findByEmail(login);
        if (user.isEmpty()) {
            user = repository.findByCode(login);
            if(user.isEmpty())
                new UsernameNotFoundException("User with username " + login + " don't exist");
        }
        return mapper.toDTO(user.get());
    }

    @Override
    public void changePassword(ChangePwd changePwd) throws Exception {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utilisateur utilisateur = repository.findByEmail(userDetails.getEmail()).orElse(null);
        if(utilisateur == null)
            throw new Exception("User not found");
        if(changePwd.getConfirmPwd() == changePwd.getNewPwd())
            throw new Exception("Password not valid");
        if(changePwd.getConfirmPwd().length() < 8)
            throw new Exception("Password not valid");
        utilisateur.setPwd(passwordEncoder.encode(changePwd.getConfirmPwd()));
        utilisateur.setIsFirstConnexion(false);
        repository.save(utilisateur);
    }

    @Override
    public Map<String, Object> dashboard() throws Exception {
        Map<String, Object> map = new HashMap<>();
        Long userLenght = repository.count();
        map.put("user", userLenght);
        Long cpLenght = iCentrePartenaireRepository.count();
        map.put("cp", cpLenght);
        Long articleLenght = iArticlesRepository.count();
        map.put("article", articleLenght);
        Long denonciationLenght = iDenonciationRepository.count();
        map.put("denonciation", denonciationLenght);
        return map;
    }

    @Override
    public void activeOrDesactive(Long id) throws Exception {
        Utilisateur utilisateur = repository.findById(id).orElse(null);
        if(utilisateur == null)
            throw new Exception("User not found");
        if(utilisateur.getStatus().equals(Status.INACTIVE))
            utilisateur.setStatus(Status.ACTIVE);
        else
            utilisateur.setStatus(Status.INACTIVE);
        repository.save(utilisateur);

    }

    @Override
    public void resetPassword(Long id) throws Exception {
        Utilisateur utilisateur = repository.findById(id).orElse(null);
        if(utilisateur == null)
            throw new Exception("User not found");
        utilisateur.setPwd(passwordEncoder.encode("123456789"));
        utilisateur.setIsFirstConnexion(true);
        repository.save(utilisateur);
    }
}
