package com.kafka.consumer;

import com.kafka.event.CommentEvent;
import com.kafka.event.CommentEventType;
import com.kafka.task.CommentAddTask;
import com.kafka.task.CommentRemoveTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class CommentEventConsumer {

    @Autowired
    CommentAddTask commentAddTask;

    @Autowired
    CommentRemoveTask commentRemoveTask;

    @Bean("comment")
    public Consumer<CommentEvent> comment() {
        return event -> {
            if(event.getType() == CommentEventType.ADD) {
                commentAddTask.processEvent(event);
            } else if(event.getType() == CommentEventType.REMOVE) {
                commentRemoveTask.processEvent(event);
            }
        };
    }

}
