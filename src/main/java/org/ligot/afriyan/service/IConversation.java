package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.ConversationDTO;
import org.springframework.data.domain.Page;


public interface IConversation {

    ConversationDTO findById(Long id);
    ConversationDTO save(ConversationDTO conversationDto);
    Page<ConversationDTO> list(int page);
    ConversationDTO update(ConversationDTO conversationDto, Long id);
    void delete(Long id);

}
