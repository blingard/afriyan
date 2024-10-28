package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.AProposDTO;
import org.ligot.afriyan.entities.APropos;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
@Mapper(componentModel = "spring")
public interface AProposMapper {
    AProposDTO toDTO(APropos aPropos);
    APropos create(AProposDTO aProposDTO);

    @Mapping(source = "id", target = "id", ignore = true)
    void update(AProposDTO aProposDTO, @MappingTarget APropos aPropos);
}
