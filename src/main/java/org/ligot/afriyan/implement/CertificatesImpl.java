package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.CertificatesDTO;
import org.ligot.afriyan.Dto.PageDTO;
import org.ligot.afriyan.entities.Certificates;
import org.ligot.afriyan.entities.Groupes;
import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.mapper.CertificatesMapper;
import org.ligot.afriyan.repository.ICertificatesRepository;
import org.ligot.afriyan.service.ICertificates;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CertificatesImpl implements ICertificates {

    private final ICertificatesRepository repository;
    private final CertificatesMapper mapper;

    public CertificatesImpl(ICertificatesRepository repository, CertificatesMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(CertificatesDTO certificatesDTO) {
        repository.save(mapper.create(certificatesDTO));
    }

    @Override
    public PageDTO<CertificatesDTO> getListAll(int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("id").descending());
        Page<Certificates> certificatesPage = repository.findAll(pageable);
        return new PageDTO<>(
                new PageImpl<>(
                        certificatesPage.stream().map(mapper::toDTOSmart).toList(),
                        pageable,
                        certificatesPage.getTotalElements()
                )
        );
    }

    @Override
    public CertificatesDTO findById(Long id) throws Exception {
        Certificates certificates = repository.findById(id).orElseThrow(()->new Exception("Certificates not found"));
        return mapper.toDTO(certificates);
    }

    @Override
    public CertificatesDTO findToUse() throws Exception {
        List<Certificates> certificates = repository.findCertificatesByStatusIsTrue();
        if(certificates.isEmpty())
            throw new Exception("Certificate not found");
        return mapper.toDTO(certificates.get(0));

    }


    @Override
    public void update(CertificatesDTO certificatesDTO, Long id) throws Exception {
        Certificates certificates = repository.findById(id).orElseThrow(()->new Exception("Certificates not found"));
        mapper.update(certificatesDTO, certificates);
        repository.save(certificates);
    }

    @Override
    public void active(Long id) throws Exception {
        Certificates certificates = repository.findById(id).orElseThrow(()->new Exception("Certificates not found"));
        if(certificates.isStatus()==false){
            List<Certificates> certificatesList = repository.findCertificatesByStatusIsTrue();
            if(!certificatesList.isEmpty()){
                certificatesList.forEach(certificate -> {
                    certificate.setStatus(Boolean.FALSE);
                    repository.save(certificate);
                });
            }
        }
        certificates.setStatus(!certificates.isStatus());
        repository.save(certificates);
    }

}
