package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.CertificatesDTO;
import org.ligot.afriyan.Dto.PageDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICertificates {
    void save(CertificatesDTO certificatesDTO);
    PageDTO<CertificatesDTO> getListAll(int page);
    CertificatesDTO findById(Long id) throws Exception;
    CertificatesDTO findToUse() throws Exception;
    void update(CertificatesDTO certificatesDTO, Long id) throws Exception;
    void active(Long id) throws Exception;
}
