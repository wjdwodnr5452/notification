package com.kafka.task;

import com.kafka.domain.Notification;
import com.kafka.domain.NotificationType;
import com.kafka.service.NotificationGetService;
import com.kafka.service.NotificationRemoveService;
import com.kafka.event.FollowEvent;
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
