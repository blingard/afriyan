package org.ligot.afriyan.service;

import org.ligot.afriyan.Dto.MessageDTO;
import org.springframework.data.domain.Page;


public interface IMessage {

    MessageDTO  findById(Long id);
    MessageDTO save(MessageDTO messageDto);
    Page<MessageDTO> list(int page);
    MessageDTO update(MessageDTO messageDto, Long id);
    void delete(Long id);

}
