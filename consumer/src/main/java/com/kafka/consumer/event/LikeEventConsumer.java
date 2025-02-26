package com.kafka.consumer.event;

import com.kafka.consumer.task.LikeAddTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class LikeEventConsumer {

    @Autowired
    private LikeAddTask likeAddTask;

    @Bean("like")
    public Consumer<LikeEvent> like() {
        return event -> {
            if(event.getType() == LikeEventType.ADD){
                likeAddTask.processEvent(event);
            }
        };
    }

}
