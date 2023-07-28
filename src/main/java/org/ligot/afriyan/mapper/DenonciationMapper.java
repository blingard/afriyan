package org.ligot.afriyan.mapper;
import org.ligot.afriyan.Dto.DenonciationDTO;
import org.ligot.afriyan.Dto.MessageDTO;
import org.ligot.afriyan.entities.Denonciation;
import org.ligot.afriyan.entities.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface DenonciationMapper {

    Denonciation create (DenonciationDTO dto);
    DenonciationDTO toDTO (Denonciation entity);
}
