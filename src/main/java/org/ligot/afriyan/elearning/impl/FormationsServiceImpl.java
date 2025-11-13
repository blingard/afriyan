package org.ligot.afriyan.elearning.impl;

import lombok.extern.slf4j.Slf4j;
import org.ligot.afriyan.elearning.dto.*;
import org.ligot.afriyan.elearning.entities.*;
import org.ligot.afriyan.elearning.mapper.ChapitresMapper;
import org.ligot.afriyan.elearning.mapper.FormationsMapper;
import org.ligot.afriyan.elearning.mapper.ParagraphsMapper;
import org.ligot.afriyan.elearning.repo.FormationUserRepo;
import org.ligot.afriyan.elearning.repo.FormationsRepo;
import org.ligot.afriyan.elearning.repo.HistoriquesLearningRepo;
import org.ligot.afriyan.elearning.service.ChapterService;
import org.ligot.afriyan.elearning.service.FormationsService;
import org.ligot.afriyan.entities.Categories;
import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.implement.UtilsService;
import org.ligot.afriyan.repository.ICategoriesRepository;
import org.ligot.afriyan.sondage.entities.Resultats;
import org.ligot.afriyan.sondage.entities.Sondage;
import org.ligot.afriyan.sondage.enumerations.TypeUserSondage;
import org.ligot.afriyan.sondage.repo.QuestionResponseRepo;
import org.ligot.afriyan.sondage.repo.ResultatsRepo;
import org.ligot.afriyan.sondage.repo.SondageRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FormationsServiceImpl implements FormationsService {

    private final FormationsRepo repo;
    private final ICategoriesRepository iCategoriesRepository;
    private final ResultatsRepo resultatsRepo;
    private final UtilsService utilsService;
    private final FormationsMapper mapper;
    private final ChapterService chapterService;
    private final FormationUserRepo formationUserRepo;
    private final SondageRepo sondageRepo;
    private final QuestionResponseRepo questionResponseRepo;
    private final HistoriquesLearningRepo historiquesLearningRepo;
    private final ChapitresMapper chapitresMapper;
    private final ParagraphsMapper paragraphsMapper;

    public FormationsServiceImpl(FormationsRepo repo, ICategoriesRepository iCategoriesRepository, ResultatsRepo resultatsRepo, UtilsService utilsService, FormationsMapper mapper, ChapterService chapterService, FormationUserRepo formationUserRepo, SondageRepo sondageRepo, QuestionResponseRepo questionResponseRepo, HistoriquesLearningRepo historiquesLearningRepo, ChapitresMapper chapitresMapper, ParagraphsMapper paragraphsMapper) {
        this.repo = repo;
        this.iCategoriesRepository = iCategoriesRepository;
        this.resultatsRepo = resultatsRepo;
        this.utilsService = utilsService;
        this.mapper = mapper;
        this.chapterService = chapterService;
        this.formationUserRepo = formationUserRepo;
        this.sondageRepo = sondageRepo;
        this.questionResponseRepo = questionResponseRepo;
        this.historiquesLearningRepo = historiquesLearningRepo;
        this.chapitresMapper = chapitresMapper;
        this.paragraphsMapper = paragraphsMapper;
    }

    @Override
    public void create(FormationsDTO formationsDTO) throws Exception {
        formationsDTO.setStatus(Boolean.FALSE.booleanValue());
        repo.save(mapper.toEntity(formationsDTO));
    }

    @Override
    public void update(Long idFormation, FormationsDTO formationsDTO) throws Exception {
        Formations formations = getById(idFormation);
        mapper.update(formationsDTO, formations);
        repo.save(formations);
    }

    @Override
    public FormationsDTO findById(Long idFormation) throws Exception {
        FormationsDTO formationsDTO = findByIdUser(idFormation);
        formationsDTO.setQuizz(null);
        return formationsDTO;
    }

    @Override
    public FormationsDTO findByIdUser(Long idFormation) throws Exception {
        Formations formations = getById(idFormation);
        if(!formations.isStatus())
            throw new Exception("Not found");
        FormationsDTO formationsDTO = mapper.toDTO(formations);
        List<ChapitresDTO> chapitresDTOS = new ArrayList<>(0);
        formationsDTO.getChapitres().forEach(chapitresDTO -> {
            try {
                ChapitresDTO chapitres = chapterService.getById(chapitresDTO.getId());
                if(chapitres.isStatus() && !chapitres.getParagraphes().isEmpty()) {
                    chapitresDTOS.add(chapitres);
                }
            } catch (Exception e) {
            }
        });
        Collections.sort(chapitresDTOS);
        formationsDTO=constructOrder(formationsDTO, chapitresDTOS);
        return formationsDTO;
    }

    @Override
    public ElearningScope findByIdUserStatus(Long idFormation) throws Exception {
        Formations formations = getById(idFormation);
        ElearningScope elearningScope = null;
        if(!formations.isStatus())
            throw new Exception("Not found");
        Utilisateur utilisateur = utilsService.getUser();
        List<ElearningModuleScope> elearningModuleScopes = new ArrayList<>();
        List<HistoriquesLearning> list = historiquesLearningRepo.findByUserIdAndFormationId(utilisateur.getId(), idFormation).stream().sorted(Comparator.comparing(HistoriquesLearning::getId)).collect(Collectors.toList());
        if(list.isEmpty()){
            FormationsDTO formationsDTO = mapper.toDTO(formations);
            formationsDTO.setChapitres(new HashSet<>());
            List<Chapitres> chapitres = formations.getChapitres().stream().sorted(Comparator.comparing(Chapitres::getId)).collect(Collectors.toList());
            for(int i =0; i<=chapitres.size()-1; i++){
                ChapitresDTO chapitresDTO = chapitresMapper.toDTO(chapitres.get(i));
                ElearningModuleScope currentModule = new ElearningModuleScope();
                chapitresDTO.setParagraphes(new HashSet<>());
                currentModule.setModule(chapitresDTO);
                currentModule.setPass(false);
                ElearningModuleScope previousModule = null;
                ElearningModuleScope nextModule = null;
                if(i==0){
                    if((chapitres.size()-1)==0){
                        previousModule = null;
                    }else {
                        nextModule = new ElearningModuleScope();
                        nextModule.setPass(false);
                        nextModule.setModule(chapitresMapper.toDTO(chapitres.get(i+1)));
                    }
                }else if(i==(chapitres.size()-1)){
                    previousModule = new ElearningModuleScope();
                    previousModule.setPass(true);
                    previousModule.setModule(chapitresMapper.toDTO(chapitres.get(i-1)));
                }else{
                    nextModule = new ElearningModuleScope();
                    nextModule.setPass(false);
                    nextModule.setModule(chapitresMapper.toDTO(chapitres.get(i+1)));
                    previousModule = new ElearningModuleScope();
                    previousModule.setPass(true);
                    previousModule.setModule(chapitresMapper.toDTO(chapitres.get(i-1)));
                }
                List<ElearningChapterScope> elearningChapterScopes = new ArrayList<>();
                List<Paragraphs> paragraphs = chapitres.get(i).getParagraphes().stream().sorted(Comparator.comparing(Paragraphs::getId)).collect(Collectors.toList());
                for(int j =0; j<=paragraphs.size()-1; j++){
                    ParagraphsDTO paragraphsDTO = paragraphsMapper.toDTO(paragraphs.get(j));
                    ElearningChapterScope currentChap = new ElearningChapterScope();
                    currentChap.setChapitre(paragraphsDTO);
                    currentChap.setPass(false);
                    ElearningChapterScope previousChap = null;
                    ElearningChapterScope nextChap = null;
                    if(j==0){
                        if((paragraphs.size()-1)==0){
                            previousChap = null;
                        }else {
                            nextChap = new ElearningChapterScope();
                            nextChap.setPass(false);
                            nextChap.setChapitre(paragraphsMapper.toDTO(paragraphs.get(j+1)));
                        }
                    }else if(j==(paragraphs.size()-1)){
                        previousChap = new ElearningChapterScope();
                        previousChap.setPass(true);
                        previousChap.setChapitre(paragraphsMapper.toDTO(paragraphs.get(j-1)));
                    }else{
                        nextChap = new ElearningChapterScope();
                        nextChap.setPass(false);
                        nextChap.setChapitre(paragraphsMapper.toDTO(paragraphs.get(j+1)));
                        previousChap = new ElearningChapterScope();
                        previousChap.setPass(true);
                        previousChap.setChapitre(paragraphsMapper.toDTO(paragraphs.get(j-1)));
                    }

                    currentChap.setChapitreNext(nextChap);
                    currentChap.setChapitrePrevious(previousChap);
                    elearningChapterScopes.add(currentChap);
                }

                currentModule.setModuleNext(nextModule);
                currentModule.setModulePrevious(previousModule);
                currentModule.setChapitres(elearningChapterScopes);
                elearningModuleScopes.add(currentModule);
            }
            elearningScope = new ElearningScope(formationsDTO, elearningModuleScopes, false);
        }else{
            FormationsDTO formationsDTO = mapper.toDTO(formations);
            formationsDTO.setChapitres(new HashSet<>());
            for (int i = 0 ; i<=list.size()-1; i++) {
                HistoriquesLearning historiquesLearning = list.get(i);
                Long currentModId = historiquesLearning.getModuleId();
                List<HistoriquesLearning> allElementCurrentIdMod = list.stream().filter(historiquesLearning1 -> historiquesLearning1.getModuleId() == currentModId).toList();
                Chapitres chapitres = formations.getChapitres().stream()
                        .filter(chapitre -> chapitre.getId() == currentModId).findFirst().orElse(null);
                if (chapitres != null) {
                    ChapitresDTO currentChapitresDTO = chapitresMapper.toDTO(chapitres);
                    ElearningModuleScope currentModule = new ElearningModuleScope();
                    //currentChapitresDTO.setParagraphes(new HashSet<>());
                    currentModule.setModule(currentChapitresDTO);
                    currentModule.setPass(true);
                    ChapitresDTO previousChapitresDTO = formations.getChapitres().stream()
                            .filter(chapitre -> chapitre.getId() == historiquesLearning.getPreviousModule()).map(chapitresMapper::toDTO)
                            .findFirst().orElse(null);
                    ElearningModuleScope previousModule = null;
                    if (previousChapitresDTO != null) {
                        previousChapitresDTO.setParagraphes(new HashSet<>());
                        previousModule = new ElearningModuleScope(previousChapitresDTO, null, null, null, true);
                    }
                    ChapitresDTO nextChapitresDTO = formations.getChapitres().stream()
                            .filter(chapitre -> chapitre.getId() == historiquesLearning.getNextModule()).map(chapitresMapper::toDTO)
                            .findFirst().orElse(null);
                    ElearningModuleScope nextModule = null;
                    if (nextChapitresDTO != null) {
                        nextChapitresDTO.setParagraphes(new HashSet<>());
                        nextModule = new ElearningModuleScope(nextChapitresDTO, null, null, null, true);
                    }
                    List<ElearningChapterScope> elearningChapterScopes = new ArrayList<>();
                    list.stream().filter(historiquesLearning1 -> ((historiquesLearning1.getFormationId() == historiquesLearning.getFormationId()) && (historiquesLearning1.getModuleId() == historiquesLearning.getModuleId()))).forEach(historiquesLearning1 -> {
                        currentChapitresDTO.getParagraphes().forEach(paragraphsDTO -> {
                            elearningChapterScopes.add(new ElearningChapterScope(paragraphsDTO, null, null, true));
                        });
                    });
                    currentChapitresDTO.setParagraphes(new HashSet<>());
                    elearningModuleScopes.add(new ElearningModuleScope(currentChapitresDTO, previousModule, nextModule, elearningChapterScopes, true));
                }
            }
            elearningScope = new ElearningScope(formationsDTO, elearningModuleScopes, false);
            formations.getChapitres().stream().sorted(Comparator.comparing(Chapitres::getId))
                    .forEach(chapitres -> {
                        List<ElearningModuleScope> remove = new ArrayList<>();
                        List<ElearningModuleScope> add = new ArrayList<>();
                        for(ElearningModuleScope elearningModuleScope : elearningModuleScopes){
                            ElearningModuleScope elearningModuleSco = elearningModuleScope;
                            if(elearningModuleScope.getModule().getId() == chapitres.getId()){
                                if(elearningModuleScope.getChapitres().size() == chapitres.getParagraphes().size()) {
                                    continue;
                                }else if(elearningModuleScope.getChapitres().size() < chapitres.getParagraphes().size()){
                                    final List<Long> listId = elearningModuleScope.getChapitres().stream().map(elearningModuleScop -> elearningModuleScop.getChapitre().getId()).toList();
                                    chapitres.getParagraphes().stream().filter(paragraphs -> !listId.contains(paragraphs.getId())).sorted(Comparator.comparing(Paragraphs::getId)).forEach(paragraphs -> {
                                        ParagraphsDTO paragraphsDTO = paragraphsMapper.toDTO(paragraphs);
                                        elearningModuleSco.getChapitres().add(new ElearningChapterScope(paragraphsDTO, null, null, false));
                                        add.add(elearningModuleSco);
                                        remove.add(elearningModuleScope);
                                    });
                                }
                            }else{
                                ChapitresDTO chapitresDTO = chapitresMapper.toDTO(chapitres);
                                ElearningModuleScope currentModule = new ElearningModuleScope();
                                chapitresDTO.setParagraphes(new HashSet<>());
                                currentModule.setModule(chapitresDTO);
                                currentModule.setPass(false);
                                ElearningModuleScope previousModule = null;
                                ElearningModuleScope nextModule = null;
                                List<ElearningChapterScope> elearningChapterScopes = new ArrayList<>();
                                List<Paragraphs> paragraphs = chapitres.getParagraphes().stream().sorted(Comparator.comparing(Paragraphs::getId)).collect(Collectors.toList());
                                for(int j =0; j<=paragraphs.size()-1; j++){
                                    ParagraphsDTO paragraphsDTO = paragraphsMapper.toDTO(paragraphs.get(j));
                                    ElearningChapterScope currentChap = new ElearningChapterScope();
                                    currentChap.setChapitre(paragraphsDTO);
                                    currentChap.setPass(false);
                                    ElearningChapterScope previousChap = null;
                                    ElearningChapterScope nextChap = null;
                                    if(j==0){
                                        if((paragraphs.size()-1)==0){
                                            previousChap = null;
                                        }else {
                                            nextChap = new ElearningChapterScope();
                                            nextChap.setPass(false);
                                            nextChap.setChapitre(paragraphsMapper.toDTO(paragraphs.get(j+1)));
                                        }
                                    }else if(j==(paragraphs.size()-1)){
                                        previousChap = new ElearningChapterScope();
                                        previousChap.setPass(true);
                                        previousChap.setChapitre(paragraphsMapper.toDTO(paragraphs.get(j-1)));
                                    }else{
                                        nextChap = new ElearningChapterScope();
                                        nextChap.setPass(false);
                                        nextChap.setChapitre(paragraphsMapper.toDTO(paragraphs.get(j+1)));
                                        previousChap = new ElearningChapterScope();
                                        previousChap.setPass(true);
                                        previousChap.setChapitre(paragraphsMapper.toDTO(paragraphs.get(j-1)));
                                    }
                                    currentChap.setChapitreNext(nextChap);
                                    currentChap.setChapitrePrevious(previousChap);
                                    elearningChapterScopes.add(currentChap);
                                    currentModule.setModuleNext(nextModule);
                                    currentModule.setModulePrevious(previousModule);
                                    currentModule.setChapitres(elearningChapterScopes);
                                    add.add(currentModule);
                                }
                            }
                        }
                        if(remove.size()>0)
                            elearningModuleScopes.removeAll(remove);
                        if(add.size()>0)
                            elearningModuleScopes.addAll(add);
            });
        }
        elearningScope.setModule(elearningModuleScopes);
        return elearningScope;
    }

    @Override
    public List<FormationsDTO> findAllByIdUser(Long idUser) throws Exception {
        List<FormationsUser> formationsUsers = formationUserRepo.findByUserId(idUser);
        return getData(formationsUsers);
    }

    @Override
    public List<FormationsDTO> findFinishByIdUser(Long idUser) throws Exception {
        List<FormationsUser> formationsUsers = formationUserRepo.findByUserIdAndFinishIsTrue(idUser);
        return getData(formationsUsers);

    }

    private List<FormationsDTO> getData(List<FormationsUser> formationsUsers){
        List<FormationsDTO> formations = new ArrayList<>(0);
        List<FormationsDTO> formationsDTOS = new ArrayList<>(0);
        formationsUsers.forEach(formationsUser -> {
            try {
                Formations formation = repo.findById(formationsUser.getFormationId()).orElseThrow();
                formationsDTOS.add(mapper.toDTO(formation));
            }catch (Exception ex){}
        });
        formationsDTOS.forEach(formationsDTO -> {
            Set<ChapitresDTO> chapitresDTOS = new HashSet<>(0);
            formationsDTO.getChapitres().forEach(chapitresDTO -> {
                Set<ParagraphsDTO> paragraphsDTOS = new HashSet<>(0);
                chapitresDTO.getParagraphes().forEach(paragraphsDTO -> {
                    if(paragraphsDTO.isStatus())
                        paragraphsDTOS.add(paragraphsDTO);
                });
                if(chapitresDTO.isStatus() && !chapitresDTO.getParagraphes().isEmpty()) {
                    ChapitresDTO dto = chapitresDTO;
                    dto.getParagraphes().clear();
                    dto.setParagraphes(paragraphsDTOS);
                    chapitresDTOS.add(dto);
                }
            });
            FormationsDTO dto = formationsDTO;
            dto.getChapitres().clear();
            dto.setChapitres(chapitresDTOS);
            if(!dto.getChapitres().isEmpty())
                formations.add(dto);
        });
        return formationsDTOS;
    }

    @Override
    public List<FormationsDTO> findNotFinishByIdUser(Long idUser) throws Exception {
        List<FormationsUser> formationsUsers = formationUserRepo.findByUserIdAndFinishIsFalse(idUser);
        return getData(formationsUsers);
    }

    @Override
    public FormationsDTO findByIdAdmin(Long idFormation) throws Exception {
        return mapper.toDTO(getById(idFormation));
    }

    @Override
    public FormationsDTO findByIdAdminWithDetail(Long idFormation) throws Exception {
        Formations formations = getById(idFormation);
        /*if(!formations.isStatus())
            throw new Exception("Not found");*/
        FormationsDTO formationsDTO = mapper.toDTO(formations);
        List<ChapitresDTO> chapitresDTOS = new ArrayList<>(0);
        formationsDTO.getChapitres().forEach(chapitresDTO -> {
            try {
                ChapitresDTO chapitres = chapterService.getByIdAdmin(chapitresDTO.getId());
                chapitresDTOS.add(chapitres);
            } catch (Exception e) {
            }
        });
        Collections.sort(chapitresDTOS);
        formationsDTO=constructOrder(formationsDTO, chapitresDTOS);
        return formationsDTO;
    }

    @Override
    public Page<FormationsDTO> findAll(int page, int size) throws Exception {
        Page<Formations> formations = repo.findAll(PageRequest.of(page, size));
        return new PageImpl<>(
                formations.get().map(mapper::toDTO).collect(Collectors.toList()),
                formations.getPageable(),
                formations.getTotalElements());
    }

    @Override
    public Page<FormationsDTO> findAllFormationOnlyAdmin(int page, int size) throws Exception {
        Page<Formations> formations = repo.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id")));
        return new PageImpl<>(
                formations.get().map(mapper::toDTOOnlyFormation).collect(Collectors.toList()),
                formations.getPageable(),
                formations.getTotalElements());
    }

    @Override
    public List<FormationsDTO> findAll() throws Exception {
        return repo.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<FormationsDTO> findAllActive() throws Exception {
        List<FormationsDTO> formationsDTOS = repo.findAll().stream().map(mapper::toDTO).filter(FormationsDTO::isStatus).collect(Collectors.toList());
        List<FormationsDTO> formations = new ArrayList<>(0);
        formationsDTOS.forEach(formationsDTO -> {
            Set<ChapitresDTO> chapitresDTOS = new HashSet<>(0);
            formationsDTO.getChapitres().forEach(chapitresDTO -> {
                Set<ParagraphsDTO> paragraphsDTOS = new HashSet<>(0);
                chapitresDTO.getParagraphes().forEach(paragraphsDTO -> {
                    if(paragraphsDTO.isStatus())
                        paragraphsDTOS.add(paragraphsDTO);
                });
                if(chapitresDTO.isStatus() && !chapitresDTO.getParagraphes().isEmpty()) {
                    ChapitresDTO dto = chapitresDTO;
                    dto.getParagraphes().clear();
                    dto.setParagraphes(paragraphsDTOS);
                    chapitresDTOS.add(dto);
                }
            });
            FormationsDTO dto = formationsDTO;
            dto.getChapitres().clear();
            dto.setChapitres(chapitresDTOS);
            if(!dto.getChapitres().isEmpty())
                formations.add(dto);
        });
        return formations;
    }

    @Override
    public List<FormationsDTO> findAllActiveLite() throws Exception {
        List<FormationsDTO> formationsDTOS = repo.findAll().stream().map(mapper::toDTO).filter(FormationsDTO::isStatus).collect(Collectors.toList());

        List<FormationsDTO> formations = new ArrayList<>(0);
        formationsDTOS.forEach(formationsDTO -> {
            Set<ChapitresDTO> chapitresDTOS = new HashSet<>(0);
            formationsDTO.getChapitres().forEach(chapitresDTO -> {
                Set<ParagraphsDTO> paragraphsDTOS = new HashSet<>(0);
                if(chapitresDTO.isStatus() && !chapitresDTO.getParagraphes().isEmpty()) {
                    ChapitresDTO dto = chapitresDTO;
                    dto.getParagraphes().clear();
                    chapitresDTOS.add(dto);
                }
            });
            FormationsDTO dto = formationsDTO;
            dto.getChapitres().clear();
            dto.setChapitres(chapitresDTOS);

            /*if(dto.getQuizz()!=null) {
                dto.getQuizz().setQuestions(new HashSet<>());
            }*/
            //if(!dto.getChapitres().isEmpty())
                formations.add(dto);
        });
        return formations;
    }

    @Override
    public FormationsDTO findByIdActiveFormationsLite(Long id) throws Exception {
        Formations formations = repo.findFormationsByIdAndStatusIsTrue(id).orElseThrow(()->new Exception("Formation non trouvee"));
        FormationsDTO formationsDTOS = mapper.toDTO(formations);
        Set<ChapitresDTO> chapitresDTOS = new HashSet<>(0);
        formations.getChapitres().forEach(chapitres -> {
            Set<ParagraphsDTO> paragraphs = new HashSet<>(0);
            chapitres.getParagraphes().forEach(paragraph -> {
                if(paragraph.isStatus())
                    paragraphs.add(new ParagraphsDTO(paragraph.getId(), paragraph.getType(), "", "",
                            paragraph.isStatus(), paragraph.getDescription()));
            });
            if(chapitres.isStatus() && !chapitres.getParagraphes().isEmpty()) {
                ChapitresDTO dto = new ChapitresDTO(chapitres.getId(), chapitres.getTitle(), chapitres.isStatus(),
                        chapitres.getOrderParagraph(),paragraphs, new HashSet<>());
                chapitresDTOS.add(dto);
            }
        });
        formationsDTOS.getChapitres().clear();
        formationsDTOS.setChapitres(chapitresDTOS);
        if(formationsDTOS.getQuizz()!=null) {
            formationsDTOS.getQuizz().setQuestions(new HashSet<>());
        }
        return formationsDTOS;
    }

    @Override
    public List<FormationsDTO> findAllActiveByCategoryLite(String categorie) {
        Categories categories = iCategoriesRepository.findById(UUID.fromString(categorie)).orElseThrow(()->new RuntimeException("Categorie non trouve"));
        if(Objects.equals(categories.isStatus(), Boolean.FALSE.booleanValue()))
            throw new RuntimeException("Categorie desactive, veillez l'activer pour l'utiliser");
        List<FormationsDTO> formationsDTOS = repo.findFormationsByCategoriesAndStatusIsTrue(categories).stream().map(mapper::toDTO).collect(Collectors.toList());
        List<FormationsDTO> formations = new ArrayList<>(0);
        formationsDTOS.forEach(formationsDTO -> {
            Set<ChapitresDTO> chapitresDTOS = new HashSet<>(0);
            formationsDTO.getChapitres().forEach(chapitresDTO -> {
                Set<ParagraphsDTO> paragraphsDTOS = new HashSet<>(0);
                if(chapitresDTO.isStatus() && !chapitresDTO.getParagraphes().isEmpty()) {
                    ChapitresDTO dto = chapitresDTO;
                    dto.getParagraphes().clear();
                    chapitresDTOS.add(dto);
                }
            });
            FormationsDTO dto = formationsDTO;
            dto.getChapitres().clear();
            dto.setChapitres(chapitresDTOS);
            if(dto.getQuizz()!=null) {
                dto.getQuizz().setQuestions(new HashSet<>());
            }
            if(!dto.getChapitres().isEmpty())
                formations.add(dto);
        });
        return formations;
    }

    @Override
    public List<FormationsDTO> findAllActiveByCategoryLiteByCode(String categorieCode) {
        Categories categories = iCategoriesRepository.findByCode(categorieCode).orElseThrow(()->new RuntimeException("Categorie non trouve"));
        List<FormationsDTO> formationsDTOS = repo.findFormationsByCategoriesAndStatusIsTrue(categories).stream().map(mapper::toDTO).collect(Collectors.toList());
        List<FormationsDTO> formations = new ArrayList<>(0);
        formationsDTOS.forEach(formationsDTO -> {
            Set<ChapitresDTO> chapitresDTOS = new HashSet<>(0);
            formationsDTO.getChapitres().forEach(chapitresDTO -> {
                Set<ParagraphsDTO> paragraphsDTOS = new HashSet<>(0);
                if(chapitresDTO.isStatus() && !chapitresDTO.getParagraphes().isEmpty()) {
                    ChapitresDTO dto = chapitresDTO;
                    dto.getParagraphes().clear();
                    chapitresDTOS.add(dto);
                }
            });
            FormationsDTO dto = formationsDTO;
            dto.getChapitres().clear();
            dto.setChapitres(chapitresDTOS);
            if(dto.getQuizz()!=null) {
                dto.getQuizz().setQuestions(new HashSet<>());
            }
            if(!dto.getChapitres().isEmpty())
                formations.add(dto);
        });
        return formations;
    }


    @Override
    public void enable(Long id) throws Exception {
        Formations formations = getById(id);
        formations.setStatus(Boolean.TRUE.booleanValue());
        repo.save(formations);
    }

    @Override
    public void finishFormation(Long idUser, Long idFormation) throws Exception {
        Optional<FormationsUser> formationsUsers = formationUserRepo.findByUserIdAndFormationId(idUser, idFormation);
        if(formationsUsers.isEmpty())
            throw new Exception("Error contacter l'administrateur du site");
        FormationsUser formationsUser = formationsUsers.get();
        formationsUser.setFinish(Boolean.TRUE.booleanValue());
        formationUserRepo.save(formationsUser);
        //Formations formations = repo.findById(idFormation).orElseThrow(()->new Exception("not found"));
        //List<QuestionResponse> questionResponses = questionResponseRepo.findQuestionResponsesBySondageId(formations.getQuizz().getId());

    }

    @Override
    public void addQuizz(Long idFormation, Long idQuizz) throws Exception {
        Formations formations = repo.findById(idFormation).orElseThrow(()->new RuntimeException("Formation non trouvee"));
        Sondage sondage = sondageRepo.findById(idQuizz).orElseThrow(()->new RuntimeException("Quizz non trouvee"));
        if(sondage.getTypeUser() != TypeUserSondage.FORMATION)
            throw new Exception("Quizz non fait pour les formations");
        if(repo.findFormationsByQuizz(sondage).isPresent())
            throw new Exception("Quizz deja utilise par une autre formation");
        formations.setQuizz(sondage);
        repo.save(formations);
    }

    @Override
    public void disable(Long id) throws Exception {
        Formations formations = getById(id);
        formations.setStatus(Boolean.FALSE.booleanValue());
        repo.save(formations);
    }

    @Override
    public Resultats certificate(Long idFormation) throws Exception {
        Utilisateur utilisateur = utilsService.getUser();
        Formations formations = new Formations();
        formations.setId(idFormation);
        return resultatsRepo.findAllByUtilisateurAndFormationAndStatusTrue(utilisateur, formations).orElseThrow(()->new Exception("Pas de certificat pour cet utilisateur"));
    }

    @Override
    public Resultats certificateAdmin(Long idUser, Long idFormation) throws Exception{
        Utilisateur utilisateur = utilsService.getUserById(idUser);
        Formations formations = new Formations();
        formations.setId(idFormation);
        return resultatsRepo.findAllByUtilisateurAndFormationAndStatusTrue(utilisateur, formations).orElseThrow(()->new Exception("Pas de certificat pour cet utilisateur"));
    }

    private Formations getById(Long id)throws Exception{
        return repo.findById(id).orElseThrow(()->new Exception("not found"));
    }
    private FormationsDTO constructOrder(FormationsDTO formationsDTO, List<ChapitresDTO> chapterList){
        String order ="";
        for(int i = 0;i<=(chapterList.size()-1);i=i+1){
            if(i==0){
                order = chapterList.get(0).getId().toString();
            }else {
                order = order+","+chapterList.get(i).getId().toString();
            }
        }
        formationsDTO.setOrderChapter(order);
        formationsDTO.getChapitres().clear();
        formationsDTO.setChapitres(chapterList.stream().collect(Collectors.toSet()));
        return formationsDTO;
    }
}
