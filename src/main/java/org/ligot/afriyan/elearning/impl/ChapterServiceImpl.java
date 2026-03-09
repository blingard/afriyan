package org.ligot.afriyan.elearning.impl;

import lombok.extern.slf4j.Slf4j;
import org.ligot.afriyan.Constantes;
import org.ligot.afriyan.config.file.FileStorageProperties;
import org.ligot.afriyan.elearning.dto.ChapitresDTO;
import org.ligot.afriyan.elearning.dto.FormationsDTO;
import org.ligot.afriyan.elearning.dto.ParagraphsDTO;
import org.ligot.afriyan.elearning.entities.Chapitres;
import org.ligot.afriyan.elearning.entities.Formations;
import org.ligot.afriyan.elearning.entities.TypeParagraph;
import org.ligot.afriyan.elearning.mapper.ChapitresMapper;
import org.ligot.afriyan.elearning.repo.ChapterRepo;
import org.ligot.afriyan.elearning.repo.FormationsRepo;
import org.ligot.afriyan.elearning.service.ChapterService;
import org.ligot.afriyan.elearning.service.ParagraphService;
import org.ligot.afriyan.implement.FileStorageService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ChapterServiceImpl implements ChapterService {
    private final ChapitresMapper mapper;
    private final ChapterRepo repo;
    private final ParagraphService paragraphService;
    private final FormationsRepo formationsRepo;
    private final FileStorageService fileStorageService;
    private final FileStorageProperties fileStorageProperties;

    public ChapterServiceImpl(ChapitresMapper mapper, ChapterRepo repo, ParagraphService paragraphService, FormationsRepo formationsRepo, FileStorageService fileStorageService, FileStorageProperties fileStorageProperties) {
        this.mapper = mapper;
        this.repo = repo;
        this.paragraphService = paragraphService;
        this.formationsRepo = formationsRepo;
        this.fileStorageService = fileStorageService;
        this.fileStorageProperties = fileStorageProperties;
    }

    @Override
    public void save(Long idFormation, ChapitresDTO chapitresDTO) throws Exception {
        Formations formations = formationsRepo.findById(idFormation).orElseThrow(()->new Exception("Formation not found"));
        if(formations.getChapitres()==null)
            formations.setChapitres(new HashSet<>(0));
        Chapitres chapitres = mapper.toEntity(chapitresDTO);
        chapitres.getParagraphes().clear();
        chapitres.setStatus(Boolean.TRUE.booleanValue());
        Chapitres chapitreSave = repo.save(chapitres);
        formations.getChapitres().add(chapitreSave);
        if(formations.getOrderChapter()==null){
            formations.setOrderChapter(chapitreSave.getId().toString());
        }else {
            formations.setOrderChapter(formations.getOrderChapter()+","+chapitreSave.getId().toString());
        }
        formationsRepo.save(formations);
    }

    @Override
    public void update(Long id, ChapitresDTO chapitresDTO) throws Exception {
        Chapitres chapitres = findById(id);
        if(!chapitres.isStatus())
            throw new Exception("Not found");
        mapper.update(chapitresDTO, chapitres);
        repo.save(chapitres);
    }

    @Override
    public void enable(Long id) throws Exception {
        Chapitres chapitres = findById(id);
        chapitres.setStatus(Boolean.TRUE.booleanValue());
        repo.save(chapitres);
    }

    @Override
    public void disable(Long id) throws Exception {
        Chapitres chapitres = findById(id);
        chapitres.setStatus(Boolean.FALSE.booleanValue());
        repo.save(chapitres);
    }

    @Override
    public ChapitresDTO getById(Long id) throws Exception {
        ChapitresDTO chapitresDTO = mapper.toDTO(findById(id));
        if(!chapitresDTO.isStatus())
            throw new Exception("Not found");
        List<ParagraphsDTO> paragraphsDTOS = new ArrayList<>(0);
        chapitresDTO.getParagraphes().forEach(paragraphsDTO -> {
            if(paragraphsDTO.isStatus()){
                ParagraphsDTO localParagraph = paragraphsDTO;
                /*if(paragraphsDTO.getType()== TypeParagraph.IMAGE){
                    String[] elements = paragraphsDTO.getContent().split(":");
                    String imageBase64 = fileStorageService.convertImageToBase64( Constantes.PARAGRAPHIMAGESUBPATH1+elements[0]);
                    String image = "data:image/"+elements[1]+";base64,"+imageBase64;
                    localParagraph.setContent(image);
                }*/
                paragraphsDTOS.add(localParagraph);
            }
        });
        Collections.sort(paragraphsDTOS);
        chapitresDTO=constructOrder(chapitresDTO, paragraphsDTOS);
        chapitresDTO.getParagraphes().clear();
        chapitresDTO.setParagraphes(paragraphsDTOS.stream().collect(Collectors.toSet()));
        return chapitresDTO;
    }

    @Override
    public ChapitresDTO getActiveById(Long id) throws Exception {
        return null;
    }

    @Override
    public ChapitresDTO getByIdAdmin(Long id) throws Exception {
        ChapitresDTO chapitresDTO = mapper.toDTO(findById(id));
        List<ParagraphsDTO> paragraphsDTOS = new ArrayList<>(0);
        chapitresDTO.getParagraphes().forEach(paragraphsDTO -> {
            ParagraphsDTO localParagraph = paragraphsDTO;
            /*if (paragraphsDTO.getType() == TypeParagraph.IMAGE) {
                String[] elements = paragraphsDTO.getContent().split(":");
                String imageBase64 = fileStorageService.convertImageToBase64("paragraph/image/" + elements[0]);
                String image = "data:image/" + elements[1] + ";base64," + imageBase64;
                localParagraph.setContent(image);
            }*/
            paragraphsDTOS.add(localParagraph);
        });
        Collections.sort(paragraphsDTOS);
        chapitresDTO = constructOrder(chapitresDTO, paragraphsDTOS);
        return chapitresDTO;
    }

    @Override
    public List<ParagraphsDTO> getByIdAdminP(Long id) throws Exception {
        ChapitresDTO chapitresDTO = getByIdAdmin(id);
        if(chapitresDTO.getParagraphes()==null)
            throw new RuntimeException("Pas de Chapitres disponible");
        if(chapitresDTO.getParagraphes().isEmpty())
            throw new RuntimeException("Pas de Chapitres disponible");
        return getByIdAdmin(id).getParagraphes().stream().map(paragraphsDTO -> {
            paragraphsDTO.setContent(null);
            return paragraphsDTO;
        }).toList();
    }

    private Chapitres findById(Long id)throws Exception{
        return repo.findById(id).orElseThrow(()->new Exception("not found"));
    }

    private ChapitresDTO constructOrder(ChapitresDTO chapitresDTO, List<ParagraphsDTO> paragraphsDTOList){
        String order ="";
        for(int i = 0;i<=(paragraphsDTOList.size()-1);i=i+1){
            if(i==0){
                order = paragraphsDTOList.get(0).getId().toString();
            }else {
                order = order+","+paragraphsDTOList.get(i).getId().toString();
            }
        }
        chapitresDTO.setOrderParagraph(order);
        chapitresDTO.getParagraphes().clear();
        chapitresDTO.setParagraphes(paragraphsDTOList.stream().collect(Collectors.toSet()));
        return chapitresDTO;
    }
}
