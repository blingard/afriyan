package org.ligot.afriyan.echo.mapper;


import org.ligot.afriyan.echo.dto.DepartementDTO;
import org.ligot.afriyan.echo.entities.Departement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartementMapper {

    Departement create (DepartementDTO departementDTO);
    DepartementDTO toDTO (Departement departement);
}
