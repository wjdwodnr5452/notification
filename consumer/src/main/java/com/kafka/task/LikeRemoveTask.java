package com.kafka.task;

import com.kafka.domain.LikeNotification;
import com.kafka.domain.Notification;
import com.kafka.domain.NotificationType;
import com.kafka.service.NotificationGetService;
import com.kafka.service.NotificationRemoveService;
import com.kafka.service.NotificationSaveService;
import com.kafka.event.LikeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Component
public class LikeRemoveTask {

    @Autowired
    NotificationGetService getService;

    @Autowired
    NotificationRemoveService removeService;

    @Autowired
    NotificationSaveService saveService;

    public void processEvent(LikeEvent event){

        Optional<Notification> notificationByTypeAndPostId = getService.getNotificationByTypeAndPostId(NotificationType.LIKE, event.getPostId());

        if (!notificationByTypeAndPostId.isPresent()){
            log.error("No notification with postId : {}", event.getPostId());
            return;
        }

        // likers 에서 event.userId 제거 1. likers 가 비었으면 알림 삭제 2. 남아 있으면 알림 업데이트
        LikeNotification notification = (LikeNotification) notificationByTypeAndPostId.get();
        removeLikerAndUpdateNotification(event, notification);

    }

    private void removeLikerAndUpdateNotification(LikeEvent event, LikeNotification notification) {
        notification.removeLikers(event.getUserId(), Instant.now());

        if (notification.getLikerIds().isEmpty()){ // 모든 유저가 좋아요를 삭제 했을 때
            removeService.deleteById(notification.getId());
        } else {
            saveService.upsert(notification);
        }
    }
}
