package com.kafka.service;

import com.kafka.domain.Notification;
import com.kafka.domain.NotificationType;
import com.kafka.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class NotificationGetService {

    @Autowired
    private NotificationRepository repository;


    public Optional<Notification> getNotificationByTypeAndCommentId(NotificationType type, Long commentId) {
        return repository.findByTypeAndCommentId(type, commentId);
    }

    public Optional<Notification> getNotificationByTypeAndPostId(NotificationType type, Long postId) {
        return repository.findByTypeAndPostId(type, postId);
    }

    public Optional<Notification> getNotificationByTypeAndUserIdAndFollowerId(NotificationType type, Long userId, Long followerId) {
        return repository.findByTypeAndUserIdAndFollowerId(type, userId, followerId);
    }

}
