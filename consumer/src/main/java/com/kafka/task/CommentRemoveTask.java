package com.kafka.task;

import com.kafka.domain.NotificationType;
import com.kafka.service.NotificationGetService;
import com.kafka.service.NotificationRemoveService;
import com.kafka.domain.Post;
import com.kafka.client.PostClient;
import com.kafka.event.CommentEvent;
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

      getService.getNotificationByTypeAndCommentId(NotificationType.COMMENT, event.getCommentId()).ifPresentOrElse(
              notification -> removeService.deleteById(notification.getId()), // 삭제한다.
                      () -> log.error("Notification not found")
      );

    }
}

