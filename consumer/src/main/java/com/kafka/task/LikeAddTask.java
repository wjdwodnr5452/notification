package com.kafka.task;

import com.kafka.domain.LikeNotification;
import com.kafka.domain.Notification;
import com.kafka.domain.NotificationType;
import com.kafka.domain.Post;
import com.kafka.client.PostClient;
import com.kafka.event.LikeEvent;
import com.kafka.service.NotificationGetService;
import com.kafka.service.NotificationSaveService;
import com.kafka.utils.NotificationIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class LikeAddTask {

    @Autowired
    private PostClient postClient;

    @Autowired
    private NotificationGetService getService;

    @Autowired
    private NotificationSaveService saveService;

    public void processEvent(LikeEvent event){
        Post post = postClient.getPost(event.getPostId());
        if(post == null) {
            log.error("Post is null with postId:{}", event.getPostId());
            return;
        }

        if (post.getUserId().equals(event.getUserId())) {
            return;
        }

        // likeNotification 1. 신규 생성 2. 업데이트
        saveService.upsert(createOrUpdateNotification(post, event));

    }

    private Notification createOrUpdateNotification(Post post, LikeEvent event){
        // 좋아요는 여러명이 누룰수 있기 때문에 기존에 있는 좋아요 알람을 조회 한다.
        Optional<Notification> notificationByTypeAndPostId = getService.getNotificationByTypeAndPostId(NotificationType.LIKE, post.getId());

        Instant now = Instant.now();
        Instant retention = now.plus(90, ChronoUnit.DAYS);

        if(notificationByTypeAndPostId.isPresent()) {
            // 있는걸 업데이트
            return updateNotification((LikeNotification) notificationByTypeAndPostId.get(), event, now, retention);
        }else{
            // 신규 생성
            return  createNotification(post, event, now, retention);
        }
    }

    private Notification updateNotification(LikeNotification notification, LikeEvent event, Instant now, Instant retention) {
        notification.addLikers(event.getUserId(), event.getCreatedAt(), now, retention);
        return notification;
    }


    private Notification createNotification(Post post, LikeEvent event, Instant now, Instant retention){

        // 신규 알림생성
        return new LikeNotification(
                NotificationIdGenerator.generate(),
                post.getUserId(),
                NotificationType.LIKE,
                event.getCreatedAt(),
                now,
                now,
                now.plus(90, ChronoUnit.DAYS),
                post.getId(),
                List.of(event.getUserId())
        );
    }

}
