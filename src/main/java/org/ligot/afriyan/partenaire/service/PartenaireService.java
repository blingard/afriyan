package org.ligot.afriyan.partenaire.service;

import org.ligot.afriyan.partenaire.dto.PartenaireCreateDTO;
import org.ligot.afriyan.partenaire.dto.PartenaireDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface PartenaireService {

    PartenaireDTO createPartenaire(PartenaireCreateDTO dto);

    PartenaireDTO updatePartenaire(UUID id, PartenaireCreateDTO dto);

    PartenaireDTO getPartenaireById(UUID id);

    List<PartenaireDTO> getAllPartenaires();

    Page<PartenaireDTO> getAllPartenaires(int page, int size);

    List<PartenaireDTO> getPartenairesByStatut(Boolean statut);

    Page<PartenaireDTO> getPartenairesByStatut(Boolean statut, int page, int size);

    List<PartenaireDTO> getPublishedPartenaires();

    Page<PartenaireDTO> getPublishedPartenaires(int page, int size);

    List<PartenaireDTO> getActiveAndPublishedPartenaires();

    Page<PartenaireDTO> getActiveAndPublishedPartenaires(int page, int size);

    List<PartenaireDTO> searchPartenaires(String keyword);

    Page<PartenaireDTO> searchPartenaires(String keyword, int page, int size);

    PartenaireDTO toggleStatut(UUID id);

    PartenaireDTO togglePublish(UUID id);

    void deletePartenaire(UUID id);
}
