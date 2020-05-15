package web.service.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import web.service.chat.model.ChatMessage;
import web.service.chat.service.ChatService;

@Controller
@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

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
        headerAccessor.getSessionAttributes().put("username" , chatMessage.getFromUser());
        return chatMessage;
    }

    /**
     * private chat
     */

    @MessageMapping("/private.chat.{channelId}")
    @SendTo("/topic/private.chat.{channelId}")
    public ChatMessage chatMessage(@DestinationVariable String channelId, ChatMessage chatMessage) {
        this.chatService.submitMessage(chatMessage);
        return chatMessage;
    }

}
