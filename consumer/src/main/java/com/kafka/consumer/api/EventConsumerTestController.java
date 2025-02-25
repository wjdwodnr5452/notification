package com.kafka.consumer.api;

import com.kafka.consumer.event.CommentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;

@RestController
public class EventConsumerTestController {

    @Autowired
    private Consumer<CommentEvent> comment;

    @PostMapping("/test/comment")
    public void comment(CommentEvent event) {
        comment.accept(event);
    }

}
