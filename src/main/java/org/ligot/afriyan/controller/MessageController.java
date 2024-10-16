package org.ligot.afriyan.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Map;

import org.ligot.afriyan.Dto.MessageDTO;
import org.ligot.afriyan.service.IMessage;
import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "api/message")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class    MessageController {

    IMessage message;

    @PostMapping(value = "save")
    Map<String, String> saveMessage(@RequestBody MessageDTO messageDto) throws Exception {
        //Set<Map<String, String>>
        Map<String, String> mapResponse= message.save(messageDto);
        return mapResponse;
    }

    @GetMapping(value = "/list/{page}")
    Page<MessageDTO> listMessage(@PathVariable  int page) throws Exception {
        return message.list(page);
    }

}
