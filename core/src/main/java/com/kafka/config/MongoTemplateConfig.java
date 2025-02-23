package com.kafka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static com.kafka.config.MongoTemplateConfig.MONG_TEMPLATE;

@Configuration
@ComponentScan(basePackages = "com.kafka")
@EnableMongoRepositories(
        basePackages = "com.kafka",
        mongoTemplateRef = MONG_TEMPLATE)
public class MongoTemplateConfig {

    public static final String MONG_TEMPLATE = "notificationMongoTemplate";

    @Bean(MONG_TEMPLATE)
    public MongoTemplate notificationMongoTemplate(
            MongoDatabaseFactory notificationMongoFactory,
            MongoConverter mongoConverter) {
        return new MongoTemplate(notificationMongoFactory, mongoConverter);
    }

/*    @Bean
    public MongoConverter mongoConverter(MongoDatabaseFactory notificationMongoFactory) {
        return new MappingMongoConverter(
                new DefaultDbRefResolver(notificationMongoFactory), new MongoMappingContext());
    }*/
}
