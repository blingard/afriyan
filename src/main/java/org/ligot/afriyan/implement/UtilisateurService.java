package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ligot.afriyan.Constantes;
import org.ligot.afriyan.Dto.*;
import org.ligot.afriyan.echo.dto.CommuneComityDTO;
import org.ligot.afriyan.echo.dto.CrppDTO;
import org.ligot.afriyan.echo.dto.MaireDTO;
import org.ligot.afriyan.echo.dto.PrefetDTO;
import org.ligot.afriyan.echo.entities.*;
import org.ligot.afriyan.echo.mapper.CommunityComityMapper;
import org.ligot.afriyan.echo.mapper.CrppMapper;
import org.ligot.afriyan.echo.mapper.MaireMapper;
import org.ligot.afriyan.echo.mapper.PrefetMapper;
import org.ligot.afriyan.echo.repo.*;
import org.ligot.afriyan.entities.*;
import org.ligot.afriyan.init.PermissionEnum;
import org.ligot.afriyan.init.RolesName;
import org.ligot.afriyan.init.SaveListUtils;
import org.ligot.afriyan.mapper.UtilisateurMapper;
import org.ligot.afriyan.repository.*;
import org.ligot.afriyan.service.IGroupes;
import org.ligot.afriyan.service.IUtilisateur;
import org.ligot.afriyan.service.KeycloakService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;

import static org.ligot.afriyan.implement.Utils.genCode;
import static org.ligot.afriyan.implement.Utils.genDefaultCode;

@Service
public class UtilisateurService implements IUtilisateur {

    private final IUtilisateurRepository repository;
    private final UtilsService utilsService;
    private final ICentrePartenaireRepository iCentrePartenaireRepository;
    private final IArticlesRepository iArticlesRepository;
    private final IGroupes groupesService;
    private final IDenonciationRepository iDenonciationRepository;
    private final PasswordEncoder passwordEncoder;

    private final IForgetPasswordRepository iForgetPasswordRepository;
    private final UtilisateurMapper mapper;
    private final TwilioService twilioService;
    private final ExecutorService executorService;
    private final FileStorageService fileStorageService;
    private final DepartementsRepo departementsRepo;
    private final PrefetRepo prefetRepo;
    private final PrefetMapper prefetMapper;
    private final CommuneRepo communeRepo;
    private final MaireRepo maireRepo;
    private final MaireMapper maireMapper;
    private final LocalityRepo localityRepo;
    private final CrppRepository crppRepository;
    private final CrppMapper crppMapper;
    private final CommunityComityRepo communityComityRepo;
    private final CommunityComityMapper communityComityMapper;
    private final IGroupesRepository iGroupesRepository;
    private final KeycloakService keycloakService;

    public UtilisateurService(IUtilisateurRepository repository, UtilsService utilsService, ICentrePartenaireRepository iCentrePartenaireRepository, IArticlesRepository iArticlesRepository, IGroupes groupesService, IDenonciationRepository iDenonciationRepository, @Qualifier("passwordEncoder") PasswordEncoder passwordEncoder, IForgetPasswordRepository iForgetPasswordRepository, UtilisateurMapper mapper, TwilioService twilioService, ExecutorService executorService, FileStorageService fileStorageService, DepartementsRepo departementsRepo, PrefetRepo prefetRepo, PrefetMapper prefetMapper, CommuneRepo communeRepo, MaireRepo maireRepo, MaireMapper maireMapper, LocalityRepo localityRepo, CrppRepository crppRepository, CrppMapper crppMapper, CommunityComityRepo communityComityRepo, CommunityComityMapper communityComityMapper,
                              IGroupesRepository iGroupesRepository, KeycloakService keycloakService) {
        this.repository = repository;
        this.utilsService = utilsService;
        this.iCentrePartenaireRepository = iCentrePartenaireRepository;
        this.iArticlesRepository = iArticlesRepository;
        this.groupesService = groupesService;
        this.iDenonciationRepository = iDenonciationRepository;
        this.passwordEncoder = passwordEncoder;
        this.iForgetPasswordRepository = iForgetPasswordRepository;
        this.mapper = mapper;
        this.twilioService = twilioService;
        this.executorService = executorService;
        this.fileStorageService = fileStorageService;
        this.departementsRepo = departementsRepo;
        this.prefetRepo = prefetRepo;
        this.prefetMapper = prefetMapper;
        this.communeRepo = communeRepo;
        this.maireRepo = maireRepo;
        this.maireMapper = maireMapper;
        this.localityRepo = localityRepo;
        this.crppRepository = crppRepository;
        this.crppMapper = crppMapper;
        this.communityComityRepo = communityComityRepo;
        this.communityComityMapper = communityComityMapper;
        this.iGroupesRepository = iGroupesRepository;
        this.keycloakService = keycloakService;
    }

    @Override
    public void save(Utilisateur utilisateur) {
        repository.save(utilisateur);
    }

    @Override
    @Transactional
    public void removePermission(Long idUser, PermissionEnum permission) {
        Utilisateur utilisateur = repository.findById(idUser).orElseThrow(()->new RuntimeException("User not found"));
        if(utilisateur.getEffectivePermission().contains(permission)){
            if(utilisateur.getPermissionsAdd() != null && utilisateur.getPermissionsAdd().contains(permission)){
                utilisateur.getPermissionsAdd().remove(permission);
            }
            if(utilisateur.getPermissionsRemove() == null){
                utilisateur.setPermissionRemove(new HashSet<>());
            }
            utilisateur.getPermissionsRemove().add(permission);
            repository.save(utilisateur);
        }
    }

    @Override
    @Transactional
    public void addPermission(Long idUser, PermissionRequest permissions) {
        Utilisateur utilisateur = repository.findById(idUser).orElseThrow(()->new RuntimeException("User not found"));
        for (PermissionEnum permission : permissions.getPermission()){
            if(!utilisateur.getEffectivePermission().contains(permission)){
                if(utilisateur.getPermissionsRemove() != null && utilisateur.getPermissionsRemove().contains(permission)){
                    utilisateur.getPermissionsRemove().remove(permission);
                }
                if(utilisateur.getPermissionsAdd() == null){
                    utilisateur.setPermissionsAdd(new HashSet<>());
                }
                utilisateur.getPermissionsAdd().add(permission);
                repository.save(utilisateur);
            }

        }



    }

    @Override
    public UtilisateurDTO findById(Long id) throws Exception {
        Utilisateur utilisateur = repository.findById(id).orElse(null);
        if(utilisateur == null)
            throw new Exception("User with id = "+id+" don't exist");
        return findWithFile(utilisateur);
    }

    @Override
    public UtilisateurDTO findByUUID(String id) throws Exception {
        Utilisateur utilisateur = repository.findByUuid(id).orElse(null);
        if(utilisateur == null)
            throw new Exception("User with id = "+id+" don't exist");
        return findWithFile(utilisateur);
    }

    @Override
    @Transactional
    public UtilisateurDTO save(UtilisateurDTO utilisateurDTO, Long idGroupe) throws Exception {
        String pwd = genDefaultCode();
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
            saveIt(utilisateur, pwd);
            executorService.execute(()->{
                String message = "Felicitation pour votre Inscription. Login:";
                message = message+(utilisateur.getEmail()==null ? utilisateur.getCode() : utilisateur.getEmail());
                message = message+" \n Password:"+pwd;
                twilioService.sendOneSms(utilisateurDTO.getTelephone(),message);
            });
            return mapper.toDTO(utilisateur);
        }catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void saveUserFile(MultipartFile file) throws Exception {
        Groupes groupe = groupesService.findByNameEntiti(RolesName.USER.name());
        readExcelFile(file, groupe);
    }

    public void readExcelFile(MultipartFile file, Groupes groupes) throws Exception {
        if(SaveListUtils.getTOTAL() != 0)
            throw new Exception("vous avez deja un fichier encours de traitement");
        try {
            String pwd = genDefaultCode();
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            boolean isHeaderRow = true;
            int i = 0;
            SaveListUtils.setTOTAL(Integer.valueOf(sheet.getLastRowNum()));
            while (rowIterator.hasNext()) {
                try {
                    Row row = rowIterator.next();
                    i++;
                    if (isHeaderRow) {
                        isHeaderRow = false;
                        continue;
                    }
                    String nom = getCellValue(row.getCell(0));
                    String prenom = getCellValue(row.getCell(1));
                    String ddn = getCellValue(row.getCell(2));
                    String lieu = getCellValue(row.getCell(3));
                    String sexe = getCellValue(row.getCell(4));
                    String phone = getCellValue(row.getCell(7));
                    String mail = getCellValue(row.getCell(8));
                    try {

                        Utilisateur utilisateurDTO = new Utilisateur();
                        utilisateurDTO.setEmail(mail);
                        utilisateurDTO.setNom(nom);
                        utilisateurDTO.setPrenom(prenom.equals("/") ? "" : prenom);
                        utilisateurDTO.setLieu(lieu);
                        utilisateurDTO.setTelephone(phone);
                        utilisateurDTO.setStatus(Status.ACTIVE);
                        utilisateurDTO.setLocation(lieu);
                        utilisateurDTO.setIsFirstConnexion(Boolean.TRUE);
                        utilisateurDTO.setPwd(passwordEncoder.encode(pwd));
                        utilisateurDTO.setSexe(sexe.equals("Masculin") ? Sexe.HOMME : (sexe.equals("Féminin") ? Sexe.FEMME : Sexe.AUTRE));
                        boolean codeIsCreate = false;
                        String code = "";
                        while(!codeIsCreate){
                            code = genCode("US",8);
                            if(!repository.findByCode(code).isPresent())
                                codeIsCreate = true;
                        }
                        utilisateurDTO.setCode(code);
                        utilisateurDTO.setGroupe(groupes);
                        utilisateurDTO.setDdn(parseYYYYMMDDDate(ddn));
                        saveIt(utilisateurDTO, pwd);
                        executorService.execute(()->{
                            String message = "Felicitation pour votre Inscription. Login:";
                            message = message+(utilisateurDTO.getEmail()==null ? utilisateurDTO.getCode() : utilisateurDTO.getEmail());
                            message = message+" \n Password:"+genDefaultCode();
                            twilioService.sendOneSms(utilisateurDTO.getTelephone(),message);
                        });
                        SaveListUtils.setCURRENT(Integer.valueOf(i));
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
            SaveListUtils.init();
            workbook.close();
        } catch (Exception e) {
            SaveListUtils.init();
            throw new RuntimeException("Erreur lors de la lecture du fichier Excel : " + file.getName(), e);
        }
    }

    private Date parseYYYYMMDDDate(String dateString)throws Exception {
        SimpleDateFormat INPUT_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        SimpleDateFormat OUTPUT_FORMAT = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat OUTPUT_FORMAT1 = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date date = INPUT_FORMAT.parse(dateString);
            return OUTPUT_FORMAT.parse(OUTPUT_FORMAT.format(date));
        } catch (Exception e) {
            e.printStackTrace();
            return OUTPUT_FORMAT1.parse(OUTPUT_FORMAT1.format(INPUT_FORMAT.parse(dateString)));
        }
    }
    private String getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString(); // Si c'est une date
                }
                return String.valueOf((int) cell.getNumericCellValue()); // Convertir les nombres
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
    }

    @Override
    @Transactional
    public UtilisateurDTO saveAdmin(UtilisateurDTO utilisateurDTO, Long idGroupe) throws Exception {
        String pwd = genDefaultCode();
        GroupesDTO groupe = groupesService.findById(idGroupe);
        if(!groupe.getRoles().stream().map(RolesDTO::getNom).toList().contains(RolesName.ADMIN.toString()))
            throw new Exception("L'utilisateur n'est pas un administrateur");
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
            saveIt(utilisateur, pwd);
            return mapper.toDTO(utilisateur);
        }catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    @Transactional
    public PrefetDTO savePrefet(PrefetDTO prefetDTO) throws Exception {
        String pwd = genDefaultCode();
        Departement departement = departementsRepo.getReferenceById(prefetDTO.getDepartement().getId());
        List<Prefet> prefets = prefetRepo.findAllByDepartementAndActive(departement, true);
        GroupesDTO groupe = groupesService.findById(prefetDTO.getUtilisateur().getGroupe().getId());
        if(!groupe.getRoles().stream().map(RolesDTO::getNom).toList().contains(RolesName.LOCAL_AUTHORITY.toString()))
            throw new Exception("L'utilisateur n'est pas une autorite locale");
        boolean codeIsCreate = false;
        String code = "";
        while(!codeIsCreate){
            code = genCode("PR",8);
            if(!repository.findByCode(code).isPresent())
                codeIsCreate = true;
        }
        prefetDTO.getUtilisateur().setId(null);
        prefetDTO.getUtilisateur().setCode(code);
        prefetDTO.getUtilisateur().setGroupe(groupe);
        prefetDTO.getUtilisateur().setPwd(passwordEncoder.encode(pwd));
        Utilisateur utilisateur = mapper.create(prefetDTO.getUtilisateur());
        utilisateur.setStatus(Status.ACTIVE);
        utilisateur.setIsFirstConnexion(true);
        try {
            utilisateur = saveIt(utilisateur, pwd);
            prefets.forEach(prefet1 -> {
                prefet1.setActive(false);
                prefetRepo.save(prefet1);
            });
            Prefet prefet = prefetRepo.save(new Prefet(null, departement, utilisateur,true));
            return prefetMapper.toDTO(prefet);
        }catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    @Transactional
    public MaireDTO saveMaire(MaireDTO maireDTO) throws Exception {
        String pwd = genDefaultCode();
        Communes communes = communeRepo.getReferenceById(maireDTO.getCommune().getId());
        List<Maire> maires = maireRepo.findAllByCommuneAndActive(communes, true);
        GroupesDTO groupe = groupesService.findById(maireDTO.getUtilisateur().getGroupe().getId());
        if(!groupe.getRoles().stream().map(RolesDTO::getNom).toList().contains(RolesName.MAIRE.toString()))
            throw new Exception("L'utilisateur n'est pas un maire");
        boolean codeIsCreate = false;
        String code = "";
        while(!codeIsCreate){
            code = genCode("MA",8);
            if(!repository.findByCode(code).isPresent())
                codeIsCreate = true;
        }
        maireDTO.getUtilisateur().setId(null);
        maireDTO.getUtilisateur().setCode(code);
        maireDTO.getUtilisateur().setGroupe(groupe);
        maireDTO.getUtilisateur().setPwd(passwordEncoder.encode(pwd));
        Utilisateur utilisateur = mapper.create(maireDTO.getUtilisateur());
        utilisateur.setStatus(Status.ACTIVE);
        utilisateur.setIsFirstConnexion(true);
        try {
            utilisateur = saveIt(utilisateur, pwd);
            maires.forEach(maire1 -> {
                maire1.setActive(false);
                maireRepo.save(maire1);
            });
            Maire maire = maireRepo.save(new Maire(null, communes, utilisateur,true));
            return maireMapper.toDTO(maire);
        }catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    @Transactional
    public void saveCrpp(CrppDTO crppDTO) throws Exception {
        String pwd = genDefaultCode();
        Communes communes = communeRepo.getReferenceById(crppDTO.getCommune().getId());
        GroupesDTO groupe = groupesService.findById(crppDTO.getUtilisateur().get(0).getGroupe().getId());
        if(!groupe.getRoles().stream().map(RolesDTO::getNom).toList().contains(RolesName.CCPR_COMMITTEE.toString()))
            throw new Exception("L'utilisateur n'est pas un CCPR");
        boolean codeIsCreate = false;
        String code = "";
        while(!codeIsCreate){
            code = genCode("CCPR",8);
            if(!repository.findByCode(code).isPresent())
                codeIsCreate = true;
        }
        crppDTO.getUtilisateur().get(0).setId(null);
        crppDTO.getUtilisateur().get(0).setCode(code);
        crppDTO.getUtilisateur().get(0).setGroupe(groupe);
        crppDTO.getUtilisateur().get(0).setPwd(passwordEncoder.encode(pwd));
        Utilisateur utilisateur = mapper.create(crppDTO.getUtilisateur().get(0));
        utilisateur.setStatus(Status.ACTIVE);
        utilisateur.setIsFirstConnexion(true);
        System.err.println("getId = "+utilisateur.getCommunes().getId());
        System.err.println("getId = "+crppDTO.getUtilisateur().get(0).getCommunes().getId());
        System.err.println("getId = "+communes.getName());
        try {
            utilisateur = saveIt(utilisateur, pwd);
            Optional<Crpp> crppOptional = crppRepository.findAllByCommune(communes);
            Crpp crpp = new Crpp();
            if(crppOptional.isPresent()){
                crpp = crppOptional.get();
                crpp.getUtilisateur().add(utilisateur);
            }else {
                crpp = new Crpp(null, communes, List.of(utilisateur));
            }
            crppRepository.save(crpp);
        }catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    @Transactional
    public void saveCc(CommuneComityDTO communeComityDTO) throws Exception {
        String pwd = genDefaultCode();
        Localities localities = localityRepo.getReferenceById(communeComityDTO.getLocality().getId());
        GroupesDTO groupe = groupesService.findById(communeComityDTO.getUtilisateur().get(0).getGroupe().getId());
        if(!groupe.getRoles().stream().map(RolesDTO::getNom).toList().contains(RolesName.COMMUNITY_COMMITTEE.toString()))
            throw new Exception("L'utilisateur n'est pas un CC");
        boolean codeIsCreate = false;
        String code = "";
        while(!codeIsCreate){
            code = genCode("CC",8);
            if(!repository.findByCode(code).isPresent())
                codeIsCreate = true;
        }
        communeComityDTO.getUtilisateur().get(0).setId(null);
        communeComityDTO.getUtilisateur().get(0).setCode(code);
        communeComityDTO.getUtilisateur().get(0).setGroupe(groupe);
        communeComityDTO.getUtilisateur().get(0).setPwd(passwordEncoder.encode(pwd));
        Utilisateur utilisateur = mapper.create(communeComityDTO.getUtilisateur().get(0));
        utilisateur.setStatus(Status.ACTIVE);
        utilisateur.setIsFirstConnexion(true);
        try {
            utilisateur = saveIt(utilisateur, pwd);
            Optional<CommuneComity> optionalCommuneComity = communityComityRepo.findAllByLocality(localities);
            CommuneComity communeComity = new CommuneComity();
            if(optionalCommuneComity.isPresent()){
                communeComity = optionalCommuneComity.get();
                communeComity.getUtilisateur().add(utilisateur);
            }else {
                communeComity = new CommuneComity(null, localities, List.of(utilisateur));
            }
            communityComityRepo.save(communeComity);
        }catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public List<UtilisateurDTO>  getUserCP(String name) throws Exception {
        return repository.findUsersWithEffectivePermission(PermissionEnum.UPDATE_USRAJ)
                .stream().map(mapper::toDTO).toList();
    }


    private Utilisateur saveIt(Utilisateur utilisateur, String pwd)throws Exception{
        try {
            checkIfUserExist(utilisateur);
            utilisateur = repository.save(utilisateur);
            String uuid = keycloakService.createUser(utilisateur.getEmail()==null ? utilisateur.getCode() : utilisateur.getEmail(),
                    utilisateur.getEmail(), pwd, utilisateur.getPrenom(), utilisateur.getNom(), true);
            utilisateur.setUuid(uuid);
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
        if(repository.findByEmail(utilisateur.getEmail()).isPresent())
            throw new Exception("Email deja utilise");
        if(repository.findByNumero_telephone(utilisateur.getTelephone())!=null)
            throw new Exception("Numero de telephone deja utilise");
    }

    @Override
    @Transactional
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
        Communes communes = new Communes();
        communes.setId(utilisateurDTO.getCommunes().getId());
        utilisateur.setCommunes(communes);
        utilisateur.setLocation(utilisateurDTO.getLocation());
        try {
            utilisateur = saveIt(utilisateur, utilisateurDTO.getPwd());
            executorService.execute(()->{
                String message = "Felicitation pour votre Inscription. Pour vous connecter, utiliser le login ";
                    message = message+(utilisateurDTO.getEmail()==null ? utilisateurDTO.getCode() : utilisateurDTO.getEmail().trim());
                    message = message+" via le lien client.youthfp.cm";
                twilioService.sendOneSms(utilisateurDTO.getTelephone().trim(),message);
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
                utilisateurs.stream().map(this::findWithFile).toList(),
                PageRequest.of(page, 15),
                utilisateurs.getContent().size());
    }

    @Override
    public PageDTO<UtilisateurDTO> list(Long groupId, int page) throws Exception {
        if(groupId == null | groupId == 0)
            groupId = 1L;
        Pageable pageable = PageRequest.of(page, 5, Sort.by("id").descending());
        Page<Utilisateur> utilisateurPage = repository.findByGroupe(new Groupes(groupId), pageable);
        return new PageDTO<>(
                new PageImpl<>(
                        utilisateurPage.stream().map(this::findWithFile).toList(),
                        pageable,
                        utilisateurPage.getTotalElements()
                )
        );
    }

    @Override
    public PageDTO<UtilisateurDTO> search(String phone, int page) throws Exception {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("id").descending());
        Page<Utilisateur> utilisateurPage = repository.findUsersByPhoneNumberStartingWith(phone, pageable);
        return new PageDTO<>(
                new PageImpl<>(
                        utilisateurPage.stream().map(this::findWithFile).toList(),
                        pageable,
                        utilisateurPage.getTotalElements()
                )
        );
    }

    @Override
    public List<UtilisateurDTO> list() throws Exception {
        return repository.findAll().stream().map(this::findWithFile).toList();
    }

    @Override
    public List<UtilisateurDTO> list(String role) throws Exception {
        groupesService.getByRole(role);
        return this.list();
    }

    @Override
    @Transactional
    public void update(UpdateUserDTO updateUserDTO, Long id) throws Exception {
        Utilisateur utilisateur = repository.findById(id).orElse(null);
        if(utilisateur == null)
            throw new Exception("User with id "+id+" don't exist");
        if(!Objects.equals(updateUserDTO.getUtilisateur().getId(), utilisateur.getId()))
            throw new Exception("Information non concordante");
        Groupes previousGroupe = utilisateur.getGroupe();
        mapper.update(updateUserDTO.getUtilisateur(), utilisateur);
        if(!Objects.equals(utilisateur.getGroupe().getId(), updateUserDTO.getUtilisateur().getGroupe().getId())) {
            Groupes groupe = groupesService.findByIdEntiti(updateUserDTO.getUtilisateur().getGroupe().getId());
            utilisateur.setGroupe(null);
            utilisateur.setGroupe(groupe);
        }
        if(Objects.isNull(updateUserDTO.getDepartement()) && Objects.isNull(updateUserDTO.getCommune()) && Objects.isNull(updateUserDTO.getLocalities())){
            repository.save(utilisateur);
        }
        if(!Objects.isNull(updateUserDTO.getDepartement()) && Objects.isNull(updateUserDTO.getCommune()) && Objects.isNull(updateUserDTO.getLocalities())) {
            Departement departement = departementsRepo.getReferenceById(updateUserDTO.getDepartement().getId());
            List<Prefet> prefets = prefetRepo.findAllByDepartementAndActive(departement, true);
            Groupes groupe = groupesService.findByIdEntiti(updateUserDTO.getUtilisateur().getGroupe().getId());
            if(!groupe.getRoles().stream().map(Roles::getNom).toList().contains(RolesName.LOCAL_AUTHORITY.toString()))
                throw new Exception("L'utilisateur n'est pas une autorite locale");
            prefets.forEach(prefet1 -> {
                prefet1.setActive(false);
                prefetRepo.save(prefet1);
            });
            setPreviousGroup(previousGroupe, groupe);
            prefetRepo.save(new Prefet(null, departement, repository.save(utilisateur),true));
        }
        if(Objects.isNull(updateUserDTO.getDepartement()) && Objects.isNull(updateUserDTO.getCommune()) && !Objects.isNull(updateUserDTO.getLocalities())) {
            Localities localities = localityRepo.getReferenceById(updateUserDTO.getLocalities().getId());
            GroupesDTO groupe = groupesService.findById(updateUserDTO.getUtilisateur().getGroupe().getId());
            if(!groupe.getRoles().stream().map(RolesDTO::getNom).toList().contains(RolesName.COMMUNITY_COMMITTEE.toString()))
                throw new Exception("L'utilisateur n'est pas un CC");
            try {
                Optional<CommuneComity> optionalCommuneComity = communityComityRepo.findAllByLocality(localities);
                CommuneComity communeComity = new CommuneComity();
                if(optionalCommuneComity.isPresent()){
                    communeComity = optionalCommuneComity.get();
                    communeComity.getUtilisateur().add(repository.save(utilisateur));
                }else {
                    communeComity = new CommuneComity(null, localities, List.of(repository.save(utilisateur)));
                }
                communityComityRepo.save(communeComity);
            }catch (Exception ex){
                ex.printStackTrace();
                throw ex;
            }
        }
        if(Objects.isNull(updateUserDTO.getDepartement()) && !Objects.isNull(updateUserDTO.getCommune()) && Objects.isNull(updateUserDTO.getLocalities())) {
            if(previousGroupe.getRoles().stream().map(Roles::getNom).toList().contains(RolesName.MAIRE.toString())){
                Communes communes = communeRepo.getReferenceById(updateUserDTO.getCommune().getId());
                List<Maire> maires = maireRepo.findAllByCommuneAndActive(communes, true);
                GroupesDTO groupe = groupesService.findById(updateUserDTO.getUtilisateur().getGroupe().getId());
                if(!groupe.getRoles().stream().map(RolesDTO::getNom).toList().contains(RolesName.MAIRE.toString()))
                    throw new Exception("L'utilisateur n'est pas un maire");
                try {
                    maires.forEach(maire1 -> {
                        maire1.setActive(false);
                        maireRepo.save(maire1);
                    });
                    maireRepo.save(new Maire(null, communes, repository.save(utilisateur),true));
                }catch (Exception ex){
                    ex.printStackTrace();
                    throw ex;
                }
            }
            if(previousGroupe.getRoles().stream().map(Roles::getNom).toList().contains(RolesName.CCPR_COMMITTEE.toString())){
                Communes communes = communeRepo.getReferenceById(updateUserDTO.getCommune().getId());
                GroupesDTO groupe = groupesService.findById(updateUserDTO.getUtilisateur().getGroupe().getId());
                if(!groupe.getRoles().stream().map(RolesDTO::getNom).toList().contains(RolesName.CCPR_COMMITTEE.toString()))
                    throw new Exception("L'utilisateur n'est pas un CCPR");
                try {
                    Optional<Crpp> crppOptional = crppRepository.findAllByCommune(communes);
                    Crpp crpp = new Crpp();
                    if(crppOptional.isPresent()){
                        crpp = crppOptional.get();
                        crpp.getUtilisateur().add(repository.save(utilisateur));
                    }else {
                        crpp = new Crpp(null, communes, List.of(repository.save(utilisateur)));
                    }
                    crppRepository.save(crpp);
                }catch (Exception ex){
                    ex.printStackTrace();
                    throw ex;
                }
            }
        }
    }

    private void setPreviousGroup(Groupes previous,Groupes current){
        if(previous.getId()==current.getId()) return;
        if(previous.getRoles().stream().map(Roles::getNom).toList().contains(RolesName.COMMUNITY_COMMITTEE.toString())){

        }
    }

    @Override
    @Transactional
    public UtilisateurDTO update(UtilisateurDTO utilisateurDTO, Long id) throws Exception {
        Utilisateur utilisateur = repository.findById(id).orElse(null);
        if(utilisateur == null)
            throw new Exception("User with id "+id+" don't exist");
        if(!Objects.equals(utilisateurDTO.getId(), utilisateur.getId()))
            throw new Exception("Information non concordante");
            mapper.update(utilisateurDTO, utilisateur);
        if(!Objects.equals(utilisateur.getGroupe().getId(), utilisateurDTO.getGroupe().getId())) {
            Groupes groupe = groupesService.findByIdEntiti(utilisateurDTO.getGroupe().getId());
            utilisateur.setGroupe(null);
            utilisateur.setGroupe(groupe);
        }
        repository.save(utilisateur);
        return findWithFile(utilisateur);
    }

    private UtilisateurDTO findWithFile(Utilisateur utilisateur){
        UtilisateurDTO utilisateurDTO = mapper.toDTO(utilisateur);
        /*try {
            String[] elements = utilisateur.getPhoto().split(":");
            String imageBase64 = fileStorageService.convertImageToBase64(Constantes.USERIMAGESUBPATH1+elements[0]);
            String image = "data:image/"+elements[1]+";base64,"+imageBase64;
            utilisateurDTO.setPhoto(image);
        }catch (Exception ex){
        }*/
        return utilisateurDTO;
    }

    @Override
    @Transactional
    public String update(MultipartFile file, Long id) throws Exception {
        Utilisateur utilisateur = utilsService.getUser();
        String name = fileStorageService.storeParagraphFileImage(file, Constantes.USERIMAGESUBPATH);
        utilisateur.setPhoto(name);
        repository.save(utilisateur);
        return findWithFile(utilisateur).getPhoto();
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
        return this.findWithFile(utilisateur);
    }

    @Override
    public UtilisateurDTO login(String login) {
        Optional<Utilisateur> user = repository.findByEmail(login);
        if (user.isEmpty()) {
            user = repository.findByCode(login);
            if(user.isEmpty())
                new UsernameNotFoundException("User with username " + login + " don't exist");
        }
        return this.findWithFile(user.get());
    }

    @Override
    public Utilisateur loginForgetPwd(String login) throws Exception {
        Optional<Utilisateur> user = repository.findByEmail(login);
        if (user.isEmpty()) {
            user = repository.findByCode(login);
            if(user.isEmpty())
                new UsernameNotFoundException("User with username " + login + " don't exist");
        }
        if(user.get().getStatus()==Status.INACTIVE){
            new UsernameNotFoundException("User with username " + login + " is block");
        }
        return user.get();
    }

    @Override
    @Transactional
    public void changePassword(ChangePwd changePwd) throws Exception {
        Utilisateur utilisateur = utilsService.getUser();
        if(changePwd.getConfirmPwd() == changePwd.getNewPwd())
            throw new Exception("Password not valid");
        if(changePwd.getConfirmPwd().length() < 8)
            throw new Exception("Password not valid");
        utilisateur.setIsFirstConnexion(false);
        keycloakService.resetPassword(utilisateur.getUuid(), changePwd.getNewPwd(), false);
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
        map.put("sexe", this.getData());
        map.put("byDate", this.getMonthlyCreationStats());
        return map;
    }

    @Override
    public Map<String, Integer> statusListSave() throws Exception {
        Map<String, Integer> map = new HashMap<>(0);
        map.put("CURRENT", SaveListUtils.getCURRENT());
        map.put("TOTAL", SaveListUtils.getTOTAL());
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
            keycloakService.resetPassword(utilisateur.getUuid(), pwd, false);
            utilisateur.setIsFirstConnexion(true);
            updateIt(utilisateur);
            executorService.execute(()->{
                String message = "Votre mot de passe a ete reinitialiser par l'administrateur. Votre nouveau mot de passe est: ";
                message = message+pwd;
                twilioService.sendOneSms(utilisateur.getTelephone().trim(),message);
            });
        }catch (Exception ex){
            throw ex;
        }
    }

    @Override
    public void forgetPassword(ForgetPasswordRequest forgetPasswordRequest) throws Exception {
        Utilisateur utilisateurDTO = loginForgetPwd(forgetPasswordRequest.getLogin().trim());
        Optional<List<ForgetPassword>> forgetPasswordOptional = Optional.ofNullable(iForgetPasswordRepository
                .findByPhoneAndActiveIsTrue(utilisateurDTO.getTelephone().trim())
                .orElseThrow(() -> new Exception("Data not found")));
        List<ForgetPassword> forgetPasswords = forgetPasswordOptional.get();
        if(forgetPasswords.isEmpty()){
            throw new Exception("Data not found");
        }
        ForgetPassword forgetPassword = null;
        if(forgetPasswords.size()==1){
            forgetPassword = forgetPasswords.get(0);
        }else {
            forgetPassword = forgetPasswords.stream()
                    .max(Comparator.comparing(ForgetPassword::getCreateDate))
                    .orElseThrow(() -> new Exception("Unable to find the most recent ForgetPassword record"));
        }
        if(!forgetPassword.getCode().trim().equals(forgetPasswordRequest.getCode().trim())){
            throw new Exception("Renseigne le code recu par SMS ");
        }
        for (ForgetPassword forgetPassword1 : forgetPasswords){
            forgetPassword1.setActive(false);
            if(!forgetPassword1.getId().equals(forgetPassword.getId())){
                iForgetPasswordRepository.save(forgetPassword1);
            }
        }
        forgetPassword.setActive(false);
        iForgetPasswordRepository.save(forgetPassword);
        utilisateurDTO.setPwd(passwordEncoder.encode(forgetPasswordRequest.getPassword().trim()));
        repository.save(utilisateurDTO);
    }

    @Override
    public List<StatistiqueMensuelleUtilisateur> getMonthlyCreationStats() {
        List<StatistiqueMensuelleUtilisateur> statistics = new ArrayList<>();
        repository.findDistinctYears().stream().forEach(integer -> {
            StatistiqueMensuelleUtilisateur stat = new StatistiqueMensuelleUtilisateur();
            stat.setYear(integer.longValue());
            List<StatistiqueMensuelle> datas = new ArrayList<>(0);
            repository.getMonthlyUserCreationStatistics(integer.intValue()).forEach(objects -> {
                StatistiqueMensuelle data = new StatistiqueMensuelle();
                data.setUv((Long) objects[1]);
                data.setName(getMonthName(Long.valueOf(objects[0].toString())));
                datas.add(data);
            });

            stat.setData(datas);
            statistics.add(stat);
        });
        return statistics;
    }

    @Override
    public Map<String, Object> getData() {
        Map<String, Object> map = new HashMap<>(0);
        map.put("all", repository.count());
        map.put("men", repository.countUtilisateursBySexe(Sexe.HOMME));
        map.put("women", repository.countUtilisateursBySexe(Sexe.FEMME));
        map.put("other", repository.countUtilisateursBySexe(Sexe.AUTRE));
        return map;
    }

    private String getMonthName(Long mounth){
        String[] monthList = {"init","Janvier", "Fevrier", "Mars", "Avril", "Mai",
                "Juin", "Juillet", "Aout", "Septembre", "Octobre",
                "Novembre", "Decembre"};
        if(mounth<1L | mounth>12L)
            return "init";
        return monthList[mounth.intValue()];
    }
}
