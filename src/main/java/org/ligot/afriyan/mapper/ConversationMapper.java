package org.ligot.afriyan.mapper;

import org.ligot.afriyan.Dto.ConversationDTO;
import org.ligot.afriyan.entities.Conversation;
import org.mapstruct.Mapper;

@Mapper( componentModel = "spring")
public interface ConversationMapper {

    Conversation create (ConversationDTO dto);
    ConversationDTO toDTO (Conversation entity);
}
