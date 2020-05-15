package web.service.chat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import web.service.chat.model.ChatChannel;

import java.util.List;

@Repository
public interface ChatChannelRepository extends MongoRepository<ChatChannel, String> {
    List<ChatChannel> findByUserId1AndAndUserId2(String userId1, String userId2);
    ChatChannel findByUuid(String uuid);
}
