package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.CentrePartenaireDTO;
import org.ligot.afriyan.Dto.CentrePartenaireSMARTDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface ICentrePartenaire {

    CentrePartenaireDTO findById(Long id) throws Exception;
    CentrePartenaireSMARTDTO findByIdClient(Long id) throws Exception;
    CentrePartenaireDTO save(MultipartFile file, CentrePartenaireDTO centrePartenaireDto) throws Exception;
    Page<CentrePartenaireDTO> list(int page) throws Exception;
    List<CentrePartenaireDTO> list() throws Exception;
    List<CentrePartenaireDTO> listAll() throws Exception;
    List<CentrePartenaireSMARTDTO> listAllUser() throws Exception;
    CentrePartenaireDTO update(CentrePartenaireDTO centrePartenaireDto, Long id) throws Exception;
    Set<CentrePartenaireSMARTDTO> usrajAdmin() throws Exception;
    void updateUser(Long userId, Long idCP) throws Exception;
    void delete(Long id) throws Exception;

    CentrePartenaireDTO findByUserId(Long id) throws Exception;

    void active(Long id);
    List<CentrePartenaireSMARTDTO> trouverCPProchesPublic(double userLat, double userLon);
    List<CentrePartenaireDTO> trouverCPProches(double userLat, double userLon);
}
