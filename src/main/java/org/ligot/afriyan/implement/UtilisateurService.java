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

import java.util.*;
import java.util.concurrent.ExecutorService;

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
    private final TwilioService twilioService;
    private final ExecutorService executorService;

    public UtilisateurService(IUtilisateurRepository repository, ICentrePartenaireRepository iCentrePartenaireRepository, IArticlesRepository iArticlesRepository, IGroupes groupesService, IDenonciationRepository iDenonciationRepository, @Qualifier("passwordEncoder") PasswordEncoder passwordEncoder, UtilisateurMapper mapper, TwilioService twilioService, ExecutorService executorService) {
        this.repository = repository;
        this.iCentrePartenaireRepository = iCentrePartenaireRepository;
        this.iArticlesRepository = iArticlesRepository;
        this.groupesService = groupesService;
        this.iDenonciationRepository = iDenonciationRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
        this.twilioService = twilioService;
        this.executorService = executorService;
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
        String pwd = genCode();
        System.err.println("saveutilisateur");
        System.err.println("pwd: "+pwd);
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
        utilisateurDTO.setPwd(passwordEncoder.encode(pwd));
        Utilisateur utilisateur = mapper.create(utilisateurDTO);
        utilisateur.setStatus(Status.ACTIVE);
        utilisateur.setIsFirstConnexion(true);
        try {
            saveIt(utilisateur);
            System.err.println("saved");
            /*executorService.execute(()->{
                String message = "Felicitation pour votre Inscription. Login:";
                message = message+(utilisateur.getEmail()==null ? utilisateur.getCode() : utilisateur.getEmail());
                message = message+" \n Password:"+pwd;
                twilioService.sendOneSms(utilisateurDTO.getNumero_telephone(),message);
            });*/
            return mapper.toDTO(utilisateur);
        }catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
    }


    private Utilisateur saveIt(Utilisateur utilisateur)throws Exception{
        try {
            checkIfUserExist(utilisateur);
            return repository.save(utilisateur);
        }catch (Exception ex){
            throw ex;
        }
    }

    private Utilisateur updateIt(Utilisateur utilisateur)throws Exception{
        try {
            return repository.save(utilisateur);
        }catch (Exception ex){
            throw ex;
        }
    }
    private void checkIfUserExist(Utilisateur utilisateur) throws Exception{
        System.err.println(utilisateur.getNumero_telephone());
        if(repository.findByEmail(utilisateur.getEmail()).isPresent())
            throw new Exception("Email deja utilise");
        if(repository.findByNumero_telephone(utilisateur.getNumero_telephone())!=null)
            throw new Exception("Numerode telephone deja utilise");
    }

    @Override
    public UtilisateurDTO register(UtilisateurDTO utilisateurDTO) throws Exception {
        boolean sendSMS = true;
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

        try {
            utilisateur = saveIt(utilisateur);
            executorService.execute(()->{
                String message = "Felicitation pour votre Inscription. Pour vous connecter, utiliser le login ";
                    message = message+(utilisateurDTO.getEmail()==null ? utilisateurDTO.getCode() : utilisateurDTO.getEmail().trim());
                    message = message+" via le lien client.youthfp.cm";
                twilioService.sendOneSms(utilisateurDTO.getNumero_telephone().trim(),message);
                });
                return mapper.toDTO(utilisateur);
        }catch (Exception ex){
            throw ex;
        }
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
        Utilisateur utilisateur = repository.findById(id).orElse(null);
        if(utilisateur == null)
            throw new Exception("User with id "+id+" don't exist");
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
        Utilisateur utilisateur = repository.findByEmail(nom).orElse(null);
        if(utilisateur == null)
            throw new Exception("User with id = "+nom+" don't exist");
        return mapper.toDTO(utilisateur);
    }

    @Override
    public UtilisateurDTO findByLogin(String login) throws Exception {
        Utilisateur utilisateur = repository.findByEmail(login).orElse(repository.findByCode(login.trim()).orElse(null));
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
        utilisateur.setPwd(passwordEncoder.encode(changePwd.getConfirmPwd().trim()));
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
        final String pwd = genCode();
        try{
            if(utilisateur == null)
                throw new Exception("User not found");
            utilisateur.setPwd(passwordEncoder.encode(pwd));
            utilisateur.setIsFirstConnexion(true);
            updateIt(utilisateur);
            executorService.execute(()->{
                String message = "Votre mot de passe a ete reinitialiser par l'administrateur. Votre nouveau mot de passe est:";
                message = message+pwd;
                twilioService.sendOneSms(utilisateur.getNumero_telephone().trim(),message);
            });
        }catch (Exception ex){}


    }
}
