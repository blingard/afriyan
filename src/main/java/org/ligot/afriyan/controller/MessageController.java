package org.ligot.afriyan.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.ligot.afriyan.Dto.MessageDTO;
import org.ligot.afriyan.implement.TwilioService;
import org.ligot.afriyan.service.IMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/message")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class    MessageController {

    IMessage message;

    @PostMapping(value = "/save")
    MessageDTO saveMessage(@RequestBody MessageDTO messageDto) throws Exception {
        return message.save(messageDto);
    }

    @GetMapping(value = "/list/{page}")
    Page<MessageDTO> listMessage(@PathVariable  int page) throws Exception {
        return message.list(page);
    }

}
