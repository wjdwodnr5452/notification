package com.kafka.config;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
//import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@Slf4j
@Configuration
public class LocalMongoConfig {

    // 도커 컨테이너
    private static final String MONGODB_IMAGE_NAME = "mongo:5.0";
    private static final int MONGODB_INNER_PORT = 27017;
    private static final String DATABASE_NAME = "notification";
    private static final GenericContainer mongo = createMongoInstance();

    private static GenericContainer createMongoInstance() { // 컨테이너 생성
        return new GenericContainer(DockerImageName.parse(MONGODB_IMAGE_NAME))
                .withExposedPorts(MONGODB_INNER_PORT)
                .withReuse(true);
    }

    @PostConstruct
    public void startMongo(){ // 프로젝트 실행시 몽고 컨테이너 실행
        try{
            mongo.start();
        }catch (Exception e) {
           log.error(e.getMessage(), e);
        }
    }

    @PreDestroy
    public void stopMongo() { // 프로젝트 종료시 몽고 컨테이너 종료
        try {
            mongo.stop();
        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Bean(name = "notificationMongoFactory")
    public MongoDatabaseFactory notificationMongoFactory() {
        return new SimpleMongoClientDatabaseFactory(
             connectionString()
        );
    }



    private ConnectionString connectionString() {
        String host = mongo.getHost();
        Integer port = mongo.getMappedPort(MONGODB_INNER_PORT); // 외부 포트 - 외부 포트가 자동적으로 임의대로 해줌 testcontainer 일때
        return new ConnectionString("mongodb://" + host + ":" + port + "/" + DATABASE_NAME);
    }

}
