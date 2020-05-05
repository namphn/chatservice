package web.service.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import web.service.chat.model.ChatMessage;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * Group chat
     */
    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username" , chatMessage.getSender());
        return chatMessage;
    }

    /**
     * private chat
     */

    @MessageMapping("/sendPrivateMessage")
    public void sendPrivateMessage(@Payload ChatMessage chatMessage) {
        simpMessagingTemplate.convertAndSendToUser(chatMessage.getReceiver().trim(), "/reply", chatMessage);
    }

    @MessageMapping("/addPrivateUser")
    @SendTo("/queue/reply")
    public ChatMessage addPrivateUser(@Payload ChatMessage chatMessage,
                                      SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("private-username", chatMessage.getSender());
        return chatMessage;
    }

}
