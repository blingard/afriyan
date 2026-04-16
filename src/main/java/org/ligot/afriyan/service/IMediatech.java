package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface IMediatech {
    void save(MultipartFile file, GalleryRequest galleryRequest) throws Exception;
    void update(GalleryRequestUpdate galleryRequestUpdate, UUID id) throws Exception;
    GalleryDTO findById(UUID id);
    List<MediatechDTO> findAll();
    List<MediatechDTO> findAllActive();
    List<GalleryDTO> findAllActiveGallery();
    PageDTO<GalleryDTO> findAllActiveGallery(Pageable pageable);
    void activeOrDesable(Long id);
    void activeOrDesable(UUID id);
}
