package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.MessageDTO;
import org.springframework.data.domain.Page;

import java.util.Set;


public interface IMessage {

    MessageDTO  findById(Long id) throws Exception;
    MessageDTO save(MessageDTO messageDto) throws Exception;
    Page<MessageDTO> list(int page) throws Exception;

}
