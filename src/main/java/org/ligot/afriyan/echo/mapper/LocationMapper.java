package org.ligot.afriyan.echo.mapper;


import org.ligot.afriyan.echo.dto.LocalitiesDTO;
import org.ligot.afriyan.echo.entities.Localities;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    Localities create (LocalitiesDTO localitiesDTO);
    LocalitiesDTO toDTO (Localities localities);
}
