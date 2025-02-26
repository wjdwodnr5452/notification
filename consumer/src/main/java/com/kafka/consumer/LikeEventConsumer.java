package com.kafka.consumer;

import com.kafka.event.LikeEvent;
import com.kafka.event.LikeEventType;
import com.kafka.task.LikeAddTask;
import com.kafka.task.LikeRemoveTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class LikeEventConsumer {

    @Autowired
    private LikeAddTask likeAddTask;

    @Autowired
    private LikeRemoveTask likeRemoveTask;

    @Bean("like")
    public Consumer<LikeEvent> like() {
        return event -> {
            if(event.getType() == LikeEventType.ADD){
                likeAddTask.processEvent(event);
            }else if(event.getType() == LikeEventType.REMOVE){
                likeRemoveTask.processEvent(event);
            }
        };
    }

}
