package org.ligot.afriyan.echo.mapper;


import org.ligot.afriyan.Dto.MenusDTO;
import org.ligot.afriyan.echo.dto.AlertRiskTypeDTO;
import org.ligot.afriyan.echo.dto.AlertsDTO;
import org.ligot.afriyan.echo.entities.AlertRiskType;
import org.ligot.afriyan.echo.entities.Alerts;
import org.ligot.afriyan.entities.Menus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AlertRiskTypeMapper {

    AlertRiskType create (AlertRiskTypeDTO alertRiskTypeDTO);
    AlertRiskTypeDTO toDTO (AlertRiskType riskType);

    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "code", target = "code", ignore = true)
    @Mapping(source = "state", target = "state", ignore = true)
    @Mapping(source = "createdAt", target = "createdAt", ignore = true)
    void update(AlertRiskTypeDTO alertRiskTypeDTO, @MappingTarget AlertRiskType riskType);
}
