package org.ligot.afriyan.echo.mapper;


import org.ligot.afriyan.echo.dto.AlertsDTO;
import org.ligot.afriyan.echo.entities.Alerts;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AlertMapper {

    Alerts create (AlertsDTO regionDTO);
    AlertsDTO toDTO (Alerts region);
}
