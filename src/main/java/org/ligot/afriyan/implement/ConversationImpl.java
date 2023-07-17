package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.ConversationDTO;
import org.ligot.afriyan.entities.Conversation;
import org.springframework.data.domain.Page;
import org.ligot.afriyan.mapper.ConversationMapper;
import org.ligot.afriyan.repository.IConversationRepository;
import org.ligot.afriyan.service.IConversation;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ConversationImpl implements IConversation {

    private final int PAGE_SIZE = 15;
    private final ConversationMapper mapper;
    private final IConversationRepository repository;

    public ConversationImpl(ConversationMapper mapper, IConversationRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public ConversationDTO findById(Long id) throws Exception {
        Conversation conversation = repository.findById(id).orElse(null);
        if(conversation == null){
            throw new Exception("Le Conversation que vous souhaitez modifier n'existes pas");
        }
        return mapper.toDTO(conversation);
    }

    @Override
    public ConversationDTO save(ConversationDTO conversationDto) throws Exception{
        return mapper.toDTO(repository.save(mapper.create(conversationDto)));
    }

    @Override
    public Page<ConversationDTO> list(int page) {
        Page<Conversation> pages = repository.findAll(PageRequest.of(page,PAGE_SIZE));
        return new PageImpl<>(pages.map(mapper::toDTO).toList(),PageRequest.of(page,PAGE_SIZE),pages.getTotalElements());
    }

    @Override
    public ConversationDTO update(ConversationDTO conversationDto, Long id) throws Exception {
        Conversation conversation = repository.findById(id).orElse(null);
        if (conversation == null){
            throw new Exception("Le Conversationq que vous souhaitez modifier n'existes pas");
        }

        return mapper.toDTO(repository.saveAndFlush(mapper.create(conversationDto)));
    }

    @Override
    public void delete(Long id) throws Exception{
        repository.deleteById(id);
    }
}
