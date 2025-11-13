package org.ligot.afriyan.echo.mapper;


import org.ligot.afriyan.echo.dto.CrppDTO;
import org.ligot.afriyan.echo.entities.Crpp;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CrppMapper {

    Crpp create (CrppDTO crppDTO);
    CrppDTO toDTO (Crpp crpp);
}
