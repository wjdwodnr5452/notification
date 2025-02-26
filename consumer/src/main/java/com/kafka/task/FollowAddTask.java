package com.kafka.task;

import com.kafka.domain.FollowNotification;
import com.kafka.domain.NotificationType;
import com.kafka.utils.NotificationIdGenerator;
import com.kafka.service.NotificationSaveService;
import com.kafka.event.FollowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class FollowAddTask {

    @Autowired
    NotificationSaveService saveService;

    public void processEvent(FollowEvent event){
        saveService.insert(createFollowNotification(event));
    }

    private static FollowNotification createFollowNotification(FollowEvent event) {
        Instant now = Instant.now();

        FollowNotification followNotification = new FollowNotification(
                NotificationIdGenerator.generate(),
                event.getTargetUserId(),
                NotificationType.FOLLOW,
                event.getCreatedAt(),
                now,
                now,
                now.plus(90, ChronoUnit.DAYS),
                event.getUserId()
        );
        return followNotification;
    }
}
