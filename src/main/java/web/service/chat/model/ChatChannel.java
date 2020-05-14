package web.service.chat.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class ChatChannel {

    @Id
    private String uuid;

    private String userId1;

    private String userId2;
}
