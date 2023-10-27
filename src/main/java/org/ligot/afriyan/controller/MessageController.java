package org.ligot.afriyan.controller;

import org.ligot.afriyan.Dto.MessageDTO;
import org.ligot.afriyan.implement.TwilioService;
import org.ligot.afriyan.service.IMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/message")
public class    MessageController {

    @Autowired
    IMessage message;

    @Autowired
    TwilioService twilioService;

    @PostMapping(value = "/save")
    MessageDTO saveMessage(@RequestBody MessageDTO messageDto) throws Exception {
        twilioService.sendSms(messageDto.getContacts(),messageDto.getCorps());
        return message.save(messageDto);
    }

    @PutMapping(value = "/update/{id}")
    MessageDTO updateMessage(@RequestBody MessageDTO messageDto, @PathVariable Long id) throws Exception {
        twilioService.sendSms(messageDto.getContacts(),messageDto.getCorps());
        return message.update(messageDto, id);
    }

    @GetMapping(value = "/list/{page}")
    Page<MessageDTO> listMessage(@PathVariable  int page) throws Exception {
        return message.list(page);
    }

    @DeleteMapping(value = "/delete/{id}")
    void deleteMessage (@PathVariable long id) throws Exception{
        message.delete(id);
    }

}
