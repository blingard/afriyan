package org.ligot.afriyan.echo.mapper;


import org.ligot.afriyan.echo.dto.WeatherPrevisionDTO;
import org.ligot.afriyan.echo.entities.WeatherPrevision;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WeatherPrevisionMapper {

    WeatherPrevision create (WeatherPrevisionDTO weatherPrevisionDTO);
    WeatherPrevisionDTO toDTO (WeatherPrevision weatherPrevision);
}
