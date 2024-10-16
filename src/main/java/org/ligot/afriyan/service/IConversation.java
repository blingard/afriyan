package org.ligot.afriyan.service;

import java.util.Map;
import java.util.Set;

import org.ligot.afriyan.Dto.MessageDTO;
import org.springframework.data.domain.Page;


public interface IMessage {

    MessageDTO  findById(Long id) throws Exception;
    Map<String, String> save(MessageDTO messageDto) throws Exception;
    Page<MessageDTO> list(int page) throws Exception;

}
