package org.ligot.afriyan.mapper;
import org.ligot.afriyan.Dto.MessageDTO;
import org.ligot.afriyan.entities.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface MessageMapper {

    Message create (MessageDTO dto);
    MessageDTO toDTO (Message entity);
}
