package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.MessageDto;
import org.ligot.afriyan.entities.Message;

import java.util.List;

public interface IMessage {

    Message saveMessage(MessageDto messageDto);
    List<Message> listMessage();
    Message updateMessage(MessageDto messageDto, long id);
    void deleteMessage(long id);

}
