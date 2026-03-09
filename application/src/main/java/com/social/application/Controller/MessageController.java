package com.social.application.Controller;

import com.social.application.Models.Message;
import com.social.application.Services.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/message")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }
    @GetMapping
    public List<Message>fetchAllMsg(){
        return messageService.fetchAllMessage();
    }

    //hello
    @PostMapping
    public Message createMessage(@RequestBody Message message){
        Message result =messageService.insertMessage(message);
        return result;
    }

    // get mapping for the fetching message by specific userid //
    @GetMapping("/{id}")
    public Message fetchByMsgId(@PathVariable long id){
        return messageService.fetchMessageById(id);
    }


}
