package com.kafka.task;

import com.kafka.client.CommentClient;
import com.kafka.client.PostClient;
import com.kafka.domain.*;
import com.kafka.event.CommentEvent;
import com.kafka.service.NotificationSaveService;
import com.kafka.utils.NotificationIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

//@ComponentScan(basePackages = "com.kafka")
@Component
public class CommentAddTask {

    @Autowired
    PostClient postClient;

    @Autowired
    CommentClient commentClient;

    @Autowired
    NotificationSaveService saveService;

    public void processEvent(CommentEvent event) {
        // 내가 작성한 댓글인 경우 무시
        Post post = postClient.getPost(event.getPostId());
        if (Objects.equals(post.getUserId(), event.getUserId()) ) {
            return;
        }

        Comment comment = commentClient.getComment(event.getCommentId());

        // 알림 생성
        Notification notification = createNotification(post, comment);
        // 저장
        saveService.insert(notification);

    }


    private Notification createNotification(Post post, Comment comment) {
        Instant now = Instant.now();

        return new CommentNotification(
                NotificationIdGenerator.generate(),
                post.getUserId(),
                NotificationType.COMMENT,
                comment.getCreatedAt(),
                now,
                now,
                now.plus(90, ChronoUnit.DAYS),
                post.getId(),
                comment.getUserId(),
                comment.getContent(),
                comment.getId()
        );
    }

}
