package org.ligot.afriyan.echo.mapper;




import org.ligot.afriyan.echo.dto.CommunesDTO;
import org.ligot.afriyan.echo.entities.Communes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommuneMapper {

    Communes create (CommunesDTO communesDTO);
    CommunesDTO toDTO (Communes communes);
}
