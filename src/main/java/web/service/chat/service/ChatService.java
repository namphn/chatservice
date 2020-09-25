package web.service.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import web.service.chat.model.ChatChannel;
import web.service.chat.model.ChatMessage;
import web.service.chat.repository.ChatChannelRepository;
import web.service.chat.repository.ChatMessageRepository;
import web.service.chat.rpc.GetChannelRequest;

import java.util.List;

@Service
public class ChatService {
    private final int MAX_PAGABLE_CHAT_MESSAGES = 100;

    @Autowired
    private ChatChannelRepository chatChannelRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public String getExistingChanel(GetChannelRequest request) {
        List<ChatChannel> chatChannels = this.chatChannelRepository.findByUserId1AndAndUserId2(
                request.getUserId1(), request.getUserId2()
        );

        if(chatChannels == null || chatChannels.isEmpty()) {
            chatChannels = this.chatChannelRepository.findByUserId1AndAndUserId2(
                    request.getUserId2(), request.getUserId1());
        }
        return (chatChannels != null && !chatChannels.isEmpty() ? chatChannels.get(0).getUuid() : null);
    }

    public String newChatChannel(GetChannelRequest request) {
        String existingChannel = this.getExistingChanel(request);

        if(existingChannel != null) {
            return existingChannel;
        }

        ChatChannel chatChannel = new ChatChannel(request.getUserId1(), request.getUserId2());
        chatChannelRepository.save(chatChannel);

        return this.getExistingChanel(request);
    }

    public void submitMessage(ChatMessage chatMessage) {
        this.chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> getAllExistingChatMessage(String channelUuid, int page) {
        ChatChannel chatChannel = chatChannelRepository.findByUuid(channelUuid);

        Pageable sortedByPriceDesc =
                PageRequest.of(0, this.MAX_PAGABLE_CHAT_MESSAGES, Sort.by("dateTime").descending());

        List<ChatMessage> chatMessageList = chatMessageRepository.findAllByFromUserAndAndToUser(
                chatChannel.getUserId1(), chatChannel.getUserId2(),
                sortedByPriceDesc
        );

        return chatMessageList;
    }
}
