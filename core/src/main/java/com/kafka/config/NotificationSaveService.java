package com.kafka.config;

import com.kafka.Notification;
import com.kafka.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationSaveService {

    @Autowired
    private NotificationRepository repository;

    public void insert(Notification notification) {
        Notification result = repository.insert(notification);
        log.info("inserted: {}", result);
    }
    
    public void upsert(Notification notification) {
        Notification result = repository.save(notification);
        log.info("updated: {}", result);
    }

}
