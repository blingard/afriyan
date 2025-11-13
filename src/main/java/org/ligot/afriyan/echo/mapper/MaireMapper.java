package org.ligot.afriyan.echo.mapper;


import org.ligot.afriyan.echo.dto.MaireDTO;
import org.ligot.afriyan.echo.entities.Maire;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaireMapper {

    Maire create (MaireDTO maireDTO);
    MaireDTO toDTO (Maire maire);
}
