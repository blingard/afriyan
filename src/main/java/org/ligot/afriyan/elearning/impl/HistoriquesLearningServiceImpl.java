package org.ligot.afriyan.elearning.impl;

import lombok.extern.slf4j.Slf4j;
import org.ligot.afriyan.elearning.dto.HistoriquesLearningDTO;
import org.ligot.afriyan.elearning.entities.Formations;
import org.ligot.afriyan.elearning.entities.FormationsUser;
import org.ligot.afriyan.elearning.entities.HistoriquesLearning;
import org.ligot.afriyan.elearning.mapper.ChapitresMapper;
import org.ligot.afriyan.elearning.mapper.HistoriquesLearningMapper;
import org.ligot.afriyan.elearning.repo.ChapterRepo;
import org.ligot.afriyan.elearning.repo.FormationUserRepo;
import org.ligot.afriyan.elearning.repo.FormationsRepo;
import org.ligot.afriyan.elearning.repo.HistoriquesLearningRepo;
import org.ligot.afriyan.elearning.service.FormationsService;
import org.ligot.afriyan.elearning.service.HistoriquesLearningService;
import org.ligot.afriyan.repository.IUtilisateurRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HistoriquesLearningServiceImpl implements HistoriquesLearningService {

    private final HistoriquesLearningRepo repo;
    private final HistoriquesLearningMapper mapper;
    private final IUtilisateurRepository utilisateurRepository;
    private final FormationsRepo formationsRepo;
    private final FormationsService formationsService;
    private final ChapterRepo chapterRepo;
    private final ChapitresMapper chapitresMapper;
    private final FormationUserRepo formationUserRepo;

    public HistoriquesLearningServiceImpl(HistoriquesLearningRepo repo, HistoriquesLearningMapper mapper, IUtilisateurRepository utilisateurRepository, FormationsRepo formationsRepo, FormationsService formationsService, ChapterRepo chapterRepo, ChapitresMapper chapitresMapper, FormationUserRepo formationUserRepo) {
        this.repo = repo;
        this.mapper = mapper;
        this.utilisateurRepository = utilisateurRepository;
        this.formationsRepo = formationsRepo;
        this.formationsService = formationsService;
        this.chapterRepo = chapterRepo;
        this.chapitresMapper = chapitresMapper;
        this.formationUserRepo = formationUserRepo;
    }

    @Override
    public void save(HistoriquesLearningDTO historiquesLearningDTO) throws Exception {
        historiquesLearningDTO.setDateLecture(Instant.now());
        historiquesLearningDTO.setStatus(Boolean.TRUE);
        utilisateurRepository.findById(historiquesLearningDTO.getUserId()).orElseThrow(()->new RuntimeException("user not found"));
        Formations formations = formationsRepo.findById(historiquesLearningDTO.getFormationId()).orElseThrow(()->new RuntimeException("formation not found"));
        formations.getChapitres().forEach(chapitres -> {
            if(!formationUserRepo.existsByUserIdAndFormationId(historiquesLearningDTO.getUserId(), historiquesLearningDTO.getFormationId())){
                formationUserRepo.save(
                        new FormationsUser(
                                null,
                                historiquesLearningDTO.getUserId(),
                                historiquesLearningDTO.getFormationId(),
                                Boolean.FALSE.booleanValue(),
                                0L
                        )
                );
            }
            if(chapitres.getId().equals(historiquesLearningDTO.getChapitreId())){
                if(repo.findByUserIdAndFormationIdAndChapitreId(historiquesLearningDTO.getUserId(), historiquesLearningDTO.getFormationId(), historiquesLearningDTO.getChapitreId()).isEmpty())
                    repo.save(mapper.toEntity(historiquesLearningDTO));
            }
        });

    }

    @Override
    public void passTest(Long id) throws Exception {

        HistoriquesLearning historiquesLearning = getById(id);
        if(!historiquesLearning.isStatus())
            throw new Exception("Historic not valid");
        historiquesLearning.setQuizzPass(Boolean.TRUE);
        repo.save(historiquesLearning);

    }

    @Override
    public List<HistoriquesLearningDTO> getAllUserHistoricOfFormation(Long userId, Long formationId) throws Exception {
        return repo.findByUserIdAndFormationId(userId, formationId).stream().sorted().map(mapper::toDTO).collect(Collectors.toList());
    }

    private HistoriquesLearning getById(Long id)throws Exception{
        return repo.findById(id).orElseThrow(()->new RuntimeException("Data not found"));
    }
}
