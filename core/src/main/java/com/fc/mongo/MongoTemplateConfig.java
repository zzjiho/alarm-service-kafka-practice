package com.fc.mongo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * MongoTemplate란?
 * Spring Data MongoDB에서 MongoDB와 상호작용하기 위한 핵심 클래스
 *
 */

// 실제 MongoTemplate 빈을 생성하고 MongoDB 리포지토리를 활성화
@Configuration
@EnableMongoRepositories(
        basePackages = "com.fc",
        mongoTemplateRef = MongoTemplateConfig.MONGO_TEMPLATE
)
public class MongoTemplateConfig {

    public static final String MONGO_TEMPLATE = "notificationMongoTemplate";

    @Bean(name = MONGO_TEMPLATE)
    public MongoTemplate notificationMongoTemplate(
            MongoDatabaseFactory notificationMongoFactory,
            MongoConverter mongoConverter) {
        return new MongoTemplate(notificationMongoFactory, mongoConverter);
    }
}
