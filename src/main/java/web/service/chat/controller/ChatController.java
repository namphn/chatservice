package web.service.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.service.chat.model.ChatChannel;
import web.service.chat.model.ChatMessage;
import web.service.chat.model.request.GetChanelRequest;
import web.service.chat.service.ChatService;

import java.util.List;

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

    @PutMapping(value="/api/private-chat/channel",produces="application/json", consumes="application/json")
    public ResponseEntity establishChatChannel(@RequestBody GetChanelRequest request) {
        String uuid = chatService.newChatChannel(request);

        if(uuid != null && !uuid.isEmpty()) {
            return new ResponseEntity(uuid, HttpStatus.OK);
        }
        else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value="/api/private-chat/channel/{channelUuid}", method= RequestMethod.GET, produces="application/json")
    public ResponseEntity<String> getExistingChatMessages(@PathVariable("channelUuid") String channelUuid, int page) {
        List<ChatMessage> messages = chatService.getAllExistingChatMessage(channelUuid, page);

        if(messages != null && !messages.isEmpty())
        {
            return new ResponseEntity(messages, HttpStatus.OK);
        }

        else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

}
