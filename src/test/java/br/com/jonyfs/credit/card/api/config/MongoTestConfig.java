package br.com.jonyfs.credit.card.api.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import java.net.InetSocketAddress;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;

@TestConfiguration
public class MongoTestConfig {

    @Bean(destroyMethod = "shutdown")
    public MongoServer mongoServer() {
        MongoServer server = new MongoServer(new MemoryBackend());
        server.bind();
        return server;
    }

    @Bean
    @Primary
    public MongoClient mongoClient(MongoServer mongoServer) {
        InetSocketAddress address = mongoServer.getLocalAddress();
        return MongoClients.create(
            "mongodb://" + address.getHostName() + ":" + address.getPort()
        );
    }

    @Bean
    @Primary
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, "credit-card");
    }
}
