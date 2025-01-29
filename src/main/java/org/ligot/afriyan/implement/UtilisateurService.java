package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ligot.afriyan.Constantes;
import org.ligot.afriyan.Dto.*;
import org.ligot.afriyan.controller.Person;
import org.ligot.afriyan.entities.*;
import org.ligot.afriyan.init.RolesName;
import org.ligot.afriyan.init.SaveListUtils;
import org.ligot.afriyan.mapper.UtilisateurMapper;
import org.ligot.afriyan.repository.*;
import org.ligot.afriyan.service.IGroupes;
import org.ligot.afriyan.service.IUtilisateur;
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

    public UtilisateurService(IUtilisateurRepository repository, ICentrePartenaireRepository iCentrePartenaireRepository, IArticlesRepository iArticlesRepository, IGroupes groupesService, IDenonciationRepository iDenonciationRepository, @Qualifier("passwordEncoder") PasswordEncoder passwordEncoder, IForgetPasswordRepository iForgetPasswordRepository, UtilisateurMapper mapper, TwilioService twilioService, ExecutorService executorService, FileStorageService fileStorageService) {
        this.repository = repository;
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
    }

    @Override
    public UtilisateurDTO findById(Long id) throws Exception {
        Utilisateur utilisateur = repository.findById(id).orElse(null);
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
            saveIt(utilisateur);
            executorService.execute(()->{
                String message = "Felicitation pour votre Inscription. Login:";
                message = message+(utilisateur.getEmail()==null ? utilisateur.getCode() : utilisateur.getEmail());
                message = message+" \n Password:"+pwd;
                twilioService.sendOneSms(utilisateurDTO.getNumero_telephone(),message);
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
                        utilisateurDTO.setNumero_telephone(phone);
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
                        saveIt(utilisateurDTO);
                        executorService.execute(()->{
                            String message = "Felicitation pour votre Inscription. Login:";
                            message = message+(utilisateurDTO.getEmail()==null ? utilisateurDTO.getCode() : utilisateurDTO.getEmail());
                            message = message+" \n Password:"+genDefaultCode();
                            twilioService.sendOneSms(utilisateurDTO.getNumero_telephone(),message);
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
            saveIt(utilisateur);
            return mapper.toDTO(utilisateur);
        }catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
    }


    @Override
    public List<UtilisateurDTO> getUserCP() throws Exception {
        List<GroupesDTO> groupesDTOList = groupesService.list();
        List<GroupesDTO> groupesDTO = new ArrayList<>(0);
        groupesDTOList.forEach(groupesDTO1 -> {
            boolean contain = false;
            for (RolesDTO rolesDTO : groupesDTO1.getRoles()){
                if(RolesName.valueOf(rolesDTO.getNom().trim()).toString()==RolesName.GESTIONNAIRECENTRE.toString())
                    contain=true;
            }
            if(contain){
                groupesDTO.add(groupesDTO1);
            }
        });
        List<UtilisateurDTO> utilisateursDtoList = new ArrayList<>(0);
        groupesDTO.forEach(groupesDTO1 -> {
            for (Utilisateur utilisateur : repository.findByGroupe(new Groupes(groupesDTO1.getId()))){
                utilisateursDtoList.add(mapper.toDTO(utilisateur));
            }

        });
        return utilisateursDtoList;
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
        if(repository.findByEmail(utilisateur.getEmail()).isPresent())
            throw new Exception("Email deja utilise");
        if(repository.findByNumero_telephone(utilisateur.getNumero_telephone())!=null)
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
    public UtilisateurDTO update(UtilisateurDTO utilisateurDTO, Long id) throws Exception {
        Utilisateur utilisateur = repository.findById(id).orElse(null);
        if(utilisateur == null)
            throw new Exception("User with id "+id+" don't exist");
        if(utilisateurDTO.getId()!=utilisateur.getId())
            throw new Exception("Information non concordante");
        mapper.update(utilisateurDTO, utilisateur);
        repository.save(utilisateur);
        return findWithFile(utilisateur);
    }

    private UtilisateurDTO findWithFile(Utilisateur utilisateur){
        UtilisateurDTO utilisateurDTO = mapper.toDTO(utilisateur);
        try {
            String[] elements = utilisateur.getPhoto().split(":");
            String imageBase64 = fileStorageService.convertImageToBase64(Constantes.USERIMAGESUBPATH1+elements[0]);
            String image = "data:image/"+elements[1]+";base64,"+imageBase64;
            utilisateurDTO.setPhoto(image);
        }catch (Exception ex){}
        return utilisateurDTO;
    }

    @Override
    @Transactional
    public String update(MultipartFile file, Long id) throws Exception {
        Utilisateur utilisateur = getUser();
        Utilisateur utilisateurSave = repository.findById(id).orElse(null);
        if(utilisateurSave==null){
            throw new Exception("Erreur: information non concordante");
        }
        if(utilisateur.getId()!=utilisateurSave.getId()){
            throw new Exception("Erreur: information non concordante");
        }
        String name = fileStorageService.storeParagraphFileImage(file, Constantes.USERIMAGESUBPATH);
        utilisateurSave.setPhoto(name);
        repository.save(utilisateurSave);
        return findWithFile(utilisateur).getPhoto();
    }

    private Utilisateur getUser() throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UtilisateurDTO utilisateurDTO = this.findByName(username);
        return mapper.create(utilisateurDTO);
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
    public UtilisateurDTO login(String login) throws Exception {
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

    @Override
    public void forgetPassword(ForgetPasswordRequest forgetPasswordRequest) throws Exception {
        Utilisateur utilisateurDTO = loginForgetPwd(forgetPasswordRequest.getLogin().trim());
        Optional<List<ForgetPassword>> forgetPasswordOptional = Optional.ofNullable(iForgetPasswordRepository
                .findByPhoneAndActiveIsTrue(utilisateurDTO.getNumero_telephone().trim())
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
