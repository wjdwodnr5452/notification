package com.kafka.consumer.task;

import com.kafka.Notification;
import com.kafka.NotificationType;
import com.kafka.config.NotificationGetService;
import com.kafka.config.NotificationRemoveService;
import com.kafka.consumer.event.FollowEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class FollowRemoveTask {

    @Autowired
    NotificationGetService notificationGetService;

    @Autowired
    NotificationRemoveService notificationRemoveService;

    public void processEvent(FollowEvent event){

        Optional<Notification> notificationByTypeAndUserId = notificationGetService.getNotificationByTypeAndUserIdAndFollowerId(NotificationType.FOLLOW, event.getTargetUserId(), event.getUserId());

        if(!notificationByTypeAndUserId.isPresent()){
            log.error("not found notificationByTypeAndUserId");
            return;
        }

        notificationRemoveService.deleteById(notificationByTypeAndUserId.get().getId());
    }
}
