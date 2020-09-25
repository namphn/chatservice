package web.service.chat.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

    private String content;
    private String userId1;
    private String userId2;
    private LocalDateTime dateTime = LocalDateTime.now();;

    public ChatMessage(String content, String userId1, String userId2) {
        this.content = content;
        this.userId1 = userId1;
        this.userId2 = userId2;
    }
}
