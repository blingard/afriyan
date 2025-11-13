package org.ligot.afriyan.echo.mapper;


import org.ligot.afriyan.echo.dto.PaysDTO;
import org.ligot.afriyan.echo.entities.Pays;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaysMapper {

    Pays create (PaysDTO paysDTO);
    PaysDTO toDTO (Pays pays);
}
