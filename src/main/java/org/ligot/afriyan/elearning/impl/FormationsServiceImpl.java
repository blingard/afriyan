package org.ligot.afriyan.elearning.impl;

import lombok.extern.slf4j.Slf4j;
import org.ligot.afriyan.elearning.dto.ChapitresDTO;
import org.ligot.afriyan.elearning.dto.FormationsDTO;
import org.ligot.afriyan.elearning.dto.ParagraphsDTO;
import org.ligot.afriyan.elearning.entities.Formations;
import org.ligot.afriyan.elearning.entities.FormationsUser;
import org.ligot.afriyan.elearning.mapper.FormationsMapper;
import org.ligot.afriyan.elearning.repo.FormationUserRepo;
import org.ligot.afriyan.elearning.repo.FormationsRepo;
import org.ligot.afriyan.elearning.service.ChapterService;
import org.ligot.afriyan.elearning.service.FormationsService;
import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.implement.UtilsService;
import org.ligot.afriyan.sondage.entities.Resultats;
import org.ligot.afriyan.sondage.entities.Sondage;
import org.ligot.afriyan.sondage.enumerations.TypeUserSondage;
import org.ligot.afriyan.sondage.repo.QuestionResponseRepo;
import org.ligot.afriyan.sondage.repo.ResultatsRepo;
import org.ligot.afriyan.sondage.repo.SondageRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FormationsServiceImpl implements FormationsService {

    private final FormationsRepo repo;
    private final ResultatsRepo resultatsRepo;
    private final UtilsService utilsService;
    private final FormationsMapper mapper;
    private final ChapterService chapterService;
    private final FormationUserRepo formationUserRepo;
    private final SondageRepo sondageRepo;
    private final QuestionResponseRepo questionResponseRepo;

    public FormationsServiceImpl(FormationsRepo repo, ResultatsRepo resultatsRepo, UtilsService utilsService, FormationsMapper mapper, ChapterService chapterService, FormationUserRepo formationUserRepo, SondageRepo sondageRepo, QuestionResponseRepo questionResponseRepo) {
        this.repo = repo;
        this.resultatsRepo = resultatsRepo;
        this.utilsService = utilsService;
        this.mapper = mapper;
        this.chapterService = chapterService;
        this.formationUserRepo = formationUserRepo;
        this.sondageRepo = sondageRepo;
        this.questionResponseRepo = questionResponseRepo;
    }

    @Override
    public void create(FormationsDTO formationsDTO) throws Exception {
        formationsDTO.setStatus(Boolean.FALSE.booleanValue());
        repo.save(mapper.toEntity(formationsDTO));
    }

    @Override
    public void update(Long idFormation, FormationsDTO formationsDTO) throws Exception {
        Formations formations = getById(idFormation);
        if(!formations.isStatus())
            throw new Exception("Not found");
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
