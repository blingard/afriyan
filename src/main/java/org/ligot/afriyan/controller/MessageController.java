package org.ligot.afriyan.controller;

import java.util.Map;

import org.ligot.afriyan.Dto.MessageDTO;
import org.ligot.afriyan.Dto.SendOneSMSDTO;
import org.ligot.afriyan.service.IMessage;
import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "api/message")
public class    MessageController {

    private final IMessage message;

    public MessageController(IMessage message) {
        this.message = message;
    }

    @PostMapping(value = "save") Map<String, String> saveMessage(@RequestBody MessageDTO messageDto) throws Exception {
        return message.save(messageDto);
    }
    @PostMapping(value = "sendsms") void sendOneSMS(@RequestBody SendOneSMSDTO messageDto) throws Exception {
        message.sendOne(messageDto);
    }

    @GetMapping(value = "/list/{page}")
    Page<MessageDTO> listMessage(@PathVariable  int page) throws Exception {
        return message.list(page);
    }

}
