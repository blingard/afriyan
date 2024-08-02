package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageImpl implements IMessage {
    MessageMapper mapper;
    IMessageRepository repository;
    VonageService vonageService;
    private final int PAGE_SIZE = 15;

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
        vonageService.sendSms(messageDTO.getContacts(),messageDTO.getCorps());
        return mapper.toDTO(repository.save(mapper.create(messageDTO)));
    }
    @Override
    public Page<MessageDTO> list(int page) throws Exception {
        Page<Message> pages = repository.findAll(PageRequest.of(page,PAGE_SIZE));
        return new PageImpl<>(pages.map(mapper::toDTO).toList(),PageRequest.of(page,PAGE_SIZE),pages.getTotalElements());
    }
}
