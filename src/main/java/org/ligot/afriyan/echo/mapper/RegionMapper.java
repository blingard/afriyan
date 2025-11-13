package org.ligot.afriyan.echo.mapper;


import org.ligot.afriyan.echo.dto.RegionDTO;
import org.ligot.afriyan.echo.entities.Region;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegionMapper {

    Region create (RegionDTO regionDTO);
    RegionDTO toDTO (Region region);
}
