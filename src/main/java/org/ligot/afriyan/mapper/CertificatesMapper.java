package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.CertificatesDTO;
import org.ligot.afriyan.entities.Certificates;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CertificatesMapper {

    CertificatesDTO toDTO(Certificates certificates);

    @Mapping(source = "contenu", target = "contenu", ignore = true)
    CertificatesDTO toDTOSmart(Certificates certificates);
    Certificates create(CertificatesDTO articlesDTO);

    @Mapping(source = "id", target = "id", ignore = true)
    void update(CertificatesDTO certificatesDTO, @MappingTarget Certificates certificates);
}
