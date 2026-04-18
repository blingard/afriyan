package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Constantes;
import org.ligot.afriyan.Dto.*;
import org.ligot.afriyan.entities.CentrePartenaire;
import org.ligot.afriyan.entities.FrontType;
import org.ligot.afriyan.entities.Sliders;
import org.ligot.afriyan.mapper.SlidersMapper;
import org.ligot.afriyan.repository.ISlidersRepository;
import org.ligot.afriyan.service.ISliders;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class SlidersImpl implements ISliders {

    private final ISlidersRepository repository;
    private final SlidersMapper mapper;
    private final FileStorageService fileStorageService;

    public SlidersImpl(ISlidersRepository repository, SlidersMapper mapper, FileStorageService fileStorageService) {
        this.repository = repository;
        this.mapper = mapper;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public void save(MultipartFile file, SlidersDTO slidersDTO) throws Exception {
        String name = fileStorageService.storeParagraphFileImage(file, Constantes.SLIDERIMAGESUBPATH);
        Sliders sliders = mapper.create(slidersDTO);
        sliders.setPhoto(name);
        repository.save(sliders);
    }

    private SlidersDTO findWithFile(Sliders centrePartenaire){
        SlidersDTO centrePartenaireDTO = mapper.toDTO(centrePartenaire);
        return centrePartenaireDTO;
    }

    @Override
    public PageDTO<SlidersDTO> getListAll(int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("id").descending());
        Page<Sliders> certificatesPage = repository.findAll(pageable);
        return new PageDTO<>(
                new PageImpl<>(
                        certificatesPage.stream().map(this::findWithFile).toList(),
                        pageable,
                        certificatesPage.getTotalElements()
                )
        );
    }

    @Override
    public List<SlidersDTO> getListAll() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public SlidersDTO findById(Long id) throws Exception {
        Sliders slide = repository.findById(id).orElseThrow(()->new Exception("Slider not found"));
        return findWithFile(slide);
    }

    @Override
    public List<SlidersDTO> findToUse() throws Exception {
        List<Sliders> sliders = repository.findSlidersByStatusIsTrue();
        if(sliders.isEmpty())
            throw new Exception("Sliders not found");
        return sliders.stream().map(this::findWithFile).toList();
    }

    @Override
    public List<SlidersSmartDTO> findToUse(FrontType frontType) throws Exception {
        List<Sliders> sliders = repository.findSlidersByStatusIsTrueAndFrontType(frontType);
        if(sliders.isEmpty())
            throw new Exception("Sliders not found");
        return sliders.stream().map(mapper::toDTOSmart).toList();
    }


    @Override
    public void update(SlidersDTO certificatesDTO, Long id) throws Exception {
        Sliders certificates = repository.findById(id).orElseThrow(()->new Exception("Sliders not found"));
        mapper.update(certificatesDTO, certificates);
        repository.save(certificates);
    }

    @Override
    public void active(Long id) throws Exception {
        Sliders certificates = repository.findById(id).orElseThrow(()->new Exception("Sliders not found"));
        certificates.setStatus(!certificates.isStatus());
        repository.save(certificates);
    }

    @Override
    public void updateFileSlide(MultipartFile file, Long id) throws Exception {
        Sliders sliders = repository.findById(id).orElseThrow(()->new Exception("Sliders not found"));
        String name = fileStorageService.storeParagraphFileImage(file, Constantes.SLIDERIMAGESUBPATH);
        sliders.setPhoto(name);
        repository.save(sliders);
    }

}
