package com.fc.mongo;

import com.mongodb.ConnectionString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

// Docker Compose로 실행 중인 MongoDB에 연결
@Profile("local")
@Configuration
public class DockerComposeMongoConfig {

    private static final String MONGO_HOST = "localhost";
    private static final int MONGO_PORT = 27017;
    private static final String DATABASE_NAME = "notification";

    @Bean(name = "notificationMongoFactory")
    public MongoDatabaseFactory notificationMongoFactory() {
        return new SimpleMongoClientDatabaseFactory(connectionString());
    }

    private ConnectionString connectionString() {
        return new ConnectionString("mongodb://" + MONGO_HOST + ":" + MONGO_PORT + "/" + DATABASE_NAME);
    }
}

