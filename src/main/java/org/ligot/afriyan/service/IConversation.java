package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.ConversationDto;
import org.ligot.afriyan.Dto.PersonneDto;
import org.ligot.afriyan.entities.Administrateur;
import org.ligot.afriyan.entities.Conversation;

import java.util.List;

public interface IConversation {

    Conversation saveConversation(ConversationDto conversationDto);
    List<Administrateur> listConversation();
    Administrateur updateConversation(ConversationDto conversationDto, long id);
    void deleteConversation(long id);

}
