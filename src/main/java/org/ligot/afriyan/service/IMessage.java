package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.MessageDto;
import org.ligot.afriyan.entities.Message;

import java.util.List;

public interface IMessage {

    Message saveMesage(MessageDto messageDto);
    List<Message> listMessage();
    Message updateMesage(MessageDto messageDto, long id);
    void deleteMesage(long id);

}
