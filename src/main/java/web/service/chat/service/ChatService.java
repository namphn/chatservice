package web.service.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.service.chat.model.ChatChannel;
import web.service.chat.model.request.GetChanelRequest;
import web.service.chat.repository.ChatChannelRepository;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    ChatChannelRepository chatChannelRepository;

    public String getExistingChanel(GetChanelRequest request) {
        List<ChatChannel> chatChannels = this.chatChannelRepository.findByUserId1AndAndUserId2(
                request.getUserId1(), request.getUserId2()
        );

        return (chatChannels != null && !chatChannels.isEmpty() ? chatChannels.get(0).getUuid() : null);
    }

}
