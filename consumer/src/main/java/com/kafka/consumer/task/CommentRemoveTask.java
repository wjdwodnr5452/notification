package com.kafka.consumer.task;

import com.kafka.Notification;
import com.kafka.NotificationType;
import com.kafka.config.NotificationGetService;
import com.kafka.config.NotificationRemoveService;
import com.kafka.config.Post;
import com.kafka.config.PostClient;
import com.kafka.consumer.event.CommentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Objects;

@Slf4j
@Component
public class CommentRemoveTask {

    @Autowired
    PostClient postClient;

    @Autowired
    NotificationGetService getService;

    @Autowired
    NotificationRemoveService removeService;

    public void processEvent(CommentEvent event){

        Post post = postClient.getPost(event.getPostId());
        if (Objects.equals(post.getUserId(), event.getUserId()) ) {
            return;
        }

      getService.getNotification(NotificationType.COMMENT, event.getCommentId()).ifPresentOrElse(
              notification -> removeService.deleteById(notification.getId()), // 삭제한다.
                      () -> log.error("Notification not found")
      );

    }
}

