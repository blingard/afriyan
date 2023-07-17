package org.ligot.afriyan.controller;

import org.ligot.afriyan.Dto.ConversationDTO;
import org.ligot.afriyan.service.IConversation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/conversation")
public class ConversationController {

    @Autowired
    IConversation conversation;

    @PostMapping(value = "/save")
    ConversationDTO saveConversation(@RequestBody ConversationDTO conversationDto) throws Exception {
        return conversation.save(conversationDto);
    }

    @PutMapping(value = "/update/{id}")
    ConversationDTO updateConversation(@RequestBody ConversationDTO conversationDto, @PathVariable Long id) throws Exception {
        return conversation.update(conversationDto, id);
    }

    @GetMapping(value = "/list/{page}")
    Page<ConversationDTO> listConversation(@PathVariable  int page) throws Exception {
        return conversation.list(page);
    }

    @DeleteMapping(value = "/delete")
    void deleteConversation (@PathVariable long id) throws Exception{
        conversation.delete(id);
    }

    @GetMapping(value = "/getById/{id}")
    ConversationDTO listById(@PathVariable Long id) throws Exception {
        return conversation.findById(id);
    }


}
