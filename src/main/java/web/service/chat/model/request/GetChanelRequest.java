package web.service.chat.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetChanelRequest {
    private String userId1;
    private String userId2;
}
