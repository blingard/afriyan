package org.ligot.afriyan.echo.mapper;


import org.ligot.afriyan.echo.dto.PrefetDTO;
import org.ligot.afriyan.echo.entities.Prefet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrefetMapper {

    Prefet create (PrefetDTO prefetDTO);
    PrefetDTO toDTO (Prefet prefet);
}
