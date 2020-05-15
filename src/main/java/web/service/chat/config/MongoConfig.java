package web.service.chat.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories
public class MongoConfig extends AbstractMongoConfiguration {

    @Override
    public MongoClient mongoClient() {
        return new MongoClient("127.0.0.1", 27018);
    }

    @Override
    protected String getDatabaseName() {
        return "chat";
    }

    @Override
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "chat");
    }
}
