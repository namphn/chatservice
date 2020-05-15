package web.service.chat.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

    private String content;
    private String fromUser;
    private String toUser;
    private LocalDateTime dateTime = LocalDateTime.now();;

    public ChatMessage(String content, String fromUser, String toUser) {
        this.content = content;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}
