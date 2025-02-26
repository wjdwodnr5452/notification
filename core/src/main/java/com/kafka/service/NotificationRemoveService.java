package com.kafka.service;

import com.kafka.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationRemoveService {

    @Autowired
    private NotificationRepository repository;


    public void deleteById(String id) {
        log.info("Deleting notification with id {}", id);
        repository.deleteById(id);
    }

}
