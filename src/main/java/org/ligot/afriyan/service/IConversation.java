package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.ConversationDTO;
import org.springframework.data.domain.Page;


public interface IConversation {

    ConversationDTO findById(Long id) throws Exception;
    ConversationDTO save(ConversationDTO conversationDto) throws Exception;
    Page<ConversationDTO> list(int page);
    ConversationDTO update(ConversationDTO conversationDto, Long id) throws Exception;
    void delete(Long id) throws Exception;

}
