package org.ligot.afriyan.echo.mapper;


import org.ligot.afriyan.echo.dto.CommuneComityDTO;
import org.ligot.afriyan.echo.entities.CommuneComity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommunityComityMapper {

    CommuneComity create (CommuneComityDTO communeComityDTO);
    CommuneComityDTO toDTO (CommuneComity communeComity);
}
