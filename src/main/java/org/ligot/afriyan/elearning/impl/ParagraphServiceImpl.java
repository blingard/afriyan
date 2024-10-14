package org.ligot.afriyan.elearning.impl;

import lombok.extern.slf4j.Slf4j;
import org.ligot.afriyan.elearning.dto.ParagraphsDTO;
import org.ligot.afriyan.elearning.entities.Chapitres;
import org.ligot.afriyan.elearning.entities.Paragraphs;
import org.ligot.afriyan.elearning.mapper.ParagraphsMapper;
import org.ligot.afriyan.elearning.repo.ChapterRepo;
import org.ligot.afriyan.elearning.repo.ParagraphRepo;
import org.ligot.afriyan.elearning.service.ParagraphService;
import org.ligot.afriyan.implement.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@Service
@Slf4j
public class ParagraphServiceImpl implements ParagraphService {
    private final ParagraphRepo repo;
    private final ParagraphsMapper mapper;
    private final ChapterRepo chapterRepo;
    private final FileStorageService fileStorageService;

    public ParagraphServiceImpl(ParagraphRepo repo, ParagraphsMapper mapper, ChapterRepo chapterRepo, FileStorageService fileStorageService) {
        this.repo = repo;
        this.mapper = mapper;
        this.chapterRepo = chapterRepo;
        this.fileStorageService = fileStorageService;
    }

    private Paragraphs saveImageParagraph(ParagraphsDTO paragraphsDTO, MultipartFile file) throws Exception{
        String name = fileStorageService.storeParagraphFileImage(file);
        Paragraphs paragraphs = mapper.toEntity(paragraphsDTO);
        String[] elements = name.split("\\.");
        name=name+":"+elements[1];
        paragraphs.setContent(name);
        return repo.save(paragraphs);
    }

    @Override
    public void save(Long idChapter, ParagraphsDTO paragraphsDTO, MultipartFile file) throws Exception {
        Chapitres chapitres = chapterRepo.findById(idChapter).orElseThrow(()->new Exception("Chapter not found"));
        Paragraphs paragraphs = switch (paragraphsDTO.getType()){
            case TEXT -> repo.save(mapper.toEntity(paragraphsDTO));
            case IMAGE -> saveImageParagraph(paragraphsDTO, file);
        };
        chapitres.getParagraphes().add(paragraphs);
       if(chapitres.getOrderParagraph()==null){
            chapitres.setOrderParagraph(paragraphs.getId().toString());
        }else {
            chapitres.setOrderParagraph(chapitres.getOrderParagraph()+","+paragraphs.getId().toString());
        }
        chapterRepo.save(chapitres);
    }

    @Override
    public void update(Long id, ParagraphsDTO paragraphsDTO) throws Exception {
        Paragraphs paragraphs = findById(id);
        if(!paragraphs.isStatus())
            throw new Exception("Paragraphe not found");
        mapper.update(paragraphsDTO, paragraphs);
        repo.save(paragraphs);
    }

    @Override
    public void enable(Long id) throws Exception {
        Paragraphs paragraphs = findById(id);
        paragraphs.setStatus(Boolean.TRUE.booleanValue());
        repo.save(paragraphs);
    }

    @Override
    public void disable(Long id) throws Exception {
        Paragraphs paragraphs = findById(id);
        paragraphs.setStatus(Boolean.FALSE.booleanValue());
        repo.save(paragraphs);
    }

    @Override
    public ParagraphsDTO getById(Long id) throws Exception {
        return mapper.toDTO(findById(id));
    }

    @Override
    public ParagraphsDTO getByIdAdmin(Long id) throws Exception {
        Paragraphs paragraphs = repo.findById(id).orElse(null);
        return mapper.toDTO(paragraphs);
    }

    private Paragraphs findById(Long id) throws Exception{
        Paragraphs paragraphs = repo.findById(id).orElseThrow(()->new Exception("Paragraph not found"));
        return paragraphs;
    }
}
