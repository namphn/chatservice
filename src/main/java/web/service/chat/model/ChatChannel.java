package web.service.chat.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class ChatChannel {

    @Id
    private String uuid;

    private String userId1;

    private String userId2;

    public ChatChannel(String userId1, String userId2) {
        this.userId1 = userId1;
        this.userId2 = userId2;
    }
}
