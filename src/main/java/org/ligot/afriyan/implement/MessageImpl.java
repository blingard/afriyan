package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.GroupesDTO;
import org.ligot.afriyan.Dto.MessageDTO;
import org.ligot.afriyan.entities.Message;
import org.ligot.afriyan.mapper.MessageMapper;
import org.ligot.afriyan.repository.IMessageRepository;
import org.ligot.afriyan.service.IGroupes;
import org.ligot.afriyan.service.IMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Transactional
public class MessageImpl implements IMessage {
    private final MessageMapper mapper;
    private final IMessageRepository repository;

    private final IGroupes iGroupes;
    private final int PAGE_SIZE = 15;

    public MessageImpl(MessageMapper mapper, IMessageRepository repository, IGroupes iGroupes) {
        this.mapper = mapper;
        this.repository = repository;
        this.iGroupes = iGroupes;
    }

    @Override
    public MessageDTO findById(Long id) throws Exception {
        Message message = repository.findById(id).orElse(null);
        if(message == null){
            throw new Exception("Le Message que vous souhaitez modifier n'existes pas");
        }
        return mapper.toDTO(message);
    }

    @Override
    public MessageDTO save(MessageDTO messageDTO) throws Exception {
        return mapper.toDTO(repository.save(mapper.create(messageDTO)));
    }

    @Override
    public Page<MessageDTO> list(int page) throws Exception {
        Page<Message> pages = repository.findAll(PageRequest.of(page,PAGE_SIZE));
        return new PageImpl<>(pages.map(mapper::toDTO).toList(),PageRequest.of(page,PAGE_SIZE),pages.getTotalElements());
    }

    @Override
    public MessageDTO update(MessageDTO messageDTO, Long id) throws Exception {
        Message message = repository.findById(id).orElse(null);
        if(message == null){
            throw new Exception("Le Message que vous souhaitez modifier n'existes pas");
        }
        messageDTO.setId(id);
        return mapper.toDTO(repository.save(mapper.create(messageDTO)));
    }

    @Override
    public void delete(Long id) throws Exception {
        repository.deleteById(id);
    }
}
