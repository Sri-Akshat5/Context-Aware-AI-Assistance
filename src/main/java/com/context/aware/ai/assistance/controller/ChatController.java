package com.context.aware.ai.assistance.controller;


import com.context.aware.ai.assistance.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
public class ChatController {


    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/query" )
    public <T> ResponseEntity<T> responseQuery(@RequestParam String q){
        String response = q;
        try{
            if(response.isBlank()){
                return new ResponseEntity("Query cannot be blank",HttpStatus.BAD_REQUEST);
            } else {
               return new ResponseEntity(chatService.responseQuery(response), HttpStatus.OK);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
