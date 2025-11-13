package org.ligot.afriyan.echo.mapper;


import org.ligot.afriyan.echo.dto.WeatherRecord;
import org.ligot.afriyan.echo.entities.Weather;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WeatherMapper {

    Weather create (WeatherRecord weatherRecord);
    WeatherRecord toDTO (Weather weather);
}
