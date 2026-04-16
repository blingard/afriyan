package org.ligot.afriyan.implement;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import org.ligot.afriyan.Dto.*;
import org.ligot.afriyan.entities.Gallery;
import org.ligot.afriyan.entities.Mediatech;
import org.ligot.afriyan.entities.Sliders;
import org.ligot.afriyan.mapper.MediatechMapper;
import org.ligot.afriyan.repository.IGalleryRepository;
import org.ligot.afriyan.repository.IMediatechRepository;
import org.ligot.afriyan.service.IMediatech;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class MediatechImpl implements IMediatech {

    private final MediatechMapper mapper;
    private final IMediatechRepository repository;
    private final IGalleryRepository iGalleryRepository;
    private final FileStorageService fileStorageService;

    public MediatechImpl(MediatechMapper mapper, IMediatechRepository repository, IGalleryRepository iGalleryRepository, FileStorageService fileStorageService) {
        this.mapper = mapper;
        this.repository = repository;
        this.iGalleryRepository = iGalleryRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public void save(MultipartFile file, @Valid GalleryRequest galleryRequest) throws Exception {
        Map<String, String> data = fileStorageService.saveGallery(file);
        try {
            galleryRequest.getType().check(data.get("extension"));
            Gallery gallery = new Gallery();
            gallery.setTitle(galleryRequest.getTitle());
            gallery.setTaille(file.getSize());
            gallery.setExtension(data.get("extension"));
            gallery.setName(data.get("name"));
            gallery.setDescription(galleryRequest.getDescription());
            gallery.setType(galleryRequest.getType());
            gallery.setPath(data.get("path"));
            gallery.setDuree(galleryRequest.getDuree());
            gallery.setActive(false);
            iGalleryRepository.save(gallery);
        }catch (Exception ex){
            fileStorageService.deleteFile(data.get("name"));
        }
    }

    @Override
    public void update(GalleryRequestUpdate galleryRequest, UUID id) throws Exception {
        Gallery gallery = iGalleryRepository.findById(id).orElseThrow(()->new RuntimeException("Not found"));
        if(!Objects.equals(id, galleryRequest.getId()))
            throw new IllegalArgumentException("information non concordante");
        gallery.setTitle(galleryRequest.getTitle());
        gallery.setDescription(galleryRequest.getDescription());
        gallery.setDuree(galleryRequest.getDuree());
        iGalleryRepository.save(gallery);
    }

    @Override
    public GalleryDTO findById(UUID id) {
        Gallery gallery = iGalleryRepository.findById(id).orElseThrow(()->new RuntimeException("Not found"));
        return new GalleryDTO(gallery.getId(), gallery.getTitle(), gallery.getName(), gallery.getDescription(),
                gallery.getType(), gallery.getPath(), gallery.getExtension(), gallery.getDuree(),
                gallery.getTaille(), gallery.isActive());
    }

    @Override
    public List<MediatechDTO> findAll() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public List<MediatechDTO> findAllActive() {
        return repository.findAllByActiveTrue().stream().map(mapper::toDTO).toList();
    }

    @Override
    public List<GalleryDTO> findAllActiveGallery() {
        return iGalleryRepository.findAllByActiveIsTrue().stream().map(gallery -> new GalleryDTO(gallery.getId(), gallery.getTitle(), gallery.getName(), gallery.getDescription(),
                gallery.getType(), gallery.getPath(), gallery.getExtension(), gallery.getDuree(),
                gallery.getTaille(), gallery.isActive())).toList();
    }

    @Override
    public PageDTO<GalleryDTO> findAllActiveGallery(Pageable pageable) {
        Page<Gallery> galleryPage = iGalleryRepository.findAll(pageable);
        return new PageDTO<>(
                new PageImpl<>(
                        galleryPage.stream().map(gallery -> new GalleryDTO(gallery.getId(), gallery.getTitle(), gallery.getName(), gallery.getDescription(),
                                        gallery.getType(), gallery.getPath(), gallery.getExtension(), gallery.getDuree(),
                                        gallery.getTaille(), gallery.isActive())).toList(),
                        pageable,
                        galleryPage.getTotalElements()
                )
        );
    }

    @Override
    public void activeOrDesable(Long id) {
        Mediatech mediatech = repository.findById(id).orElse(null);
        if(mediatech != null){
            mediatech.setActive(!mediatech.isActive());
            repository.save(mediatech);
        }
    }

    @Override
    public void activeOrDesable(UUID id) {
        Gallery gallery = iGalleryRepository.findById(id).orElseThrow(()->new RuntimeException("Not found"));
        gallery.setActive(!gallery.isActive());
        iGalleryRepository.save(gallery);
    }
}
