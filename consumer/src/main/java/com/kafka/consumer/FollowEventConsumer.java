package com.kafka.consumer;

import com.kafka.event.FollowEvent;
import com.kafka.event.FollowEventType;
import com.kafka.task.FollowAddTask;
import com.kafka.task.FollowRemoveTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class FollowEventConsumer {

    @Autowired
    FollowAddTask followAddTask;

    @Autowired
    FollowRemoveTask followRemoveTask;


    @Bean("follow")
    public Consumer<FollowEvent> follow() {
        return event -> {
            if (event.getType() == FollowEventType.ADD){
                followAddTask.processEvent(event);
            }else if(event.getType() == FollowEventType.REMOVE){
                followRemoveTask.processEvent(event);
            }
        };
    }

}
