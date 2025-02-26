package com.kafka;

import com.kafka.domain.Notification;
import com.kafka.repository.NotificationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@SpringBootApplication
@SpringBootTest
public class NotificationRepositoryMemoryImplTest {

    @Autowired
    private NotificationRepository notificationRepository;

    private final Instant now = Instant.now();
    private final Instant deletedAt = Instant.now().plus(90, ChronoUnit.DAYS);

    @BeforeEach
    public void save() {
      //  Notification saveNotification = new Notification("1", 2L, NotificationType.LIke, now, deletedAt);
      //  notificationRepository.save(saveNotification);
    }

/*    @Test
    void save() {
        Notification saveNotification = new Notification("1", 2L, NotificationType.LIke, Instant.now(), Instant.now().plus(90, ChronoUnit.DAYS));
        nrm.save(saveNotification);

        Optional<Notification> notification = nrm.findById("1");

        Assertions.assertTrue(notification.isPresent());


    }*/

    @Test
    void findById() {
        Notification notification = notificationRepository.findById("1").orElseThrow();

       /* Assertions.assertEquals(notification.id, "1");
        Assertions.assertEquals(notification.userId, 2L);
        Assertions.assertEquals(notification.createdAt.getEpochSecond(), now.getEpochSecond());
        Assertions.assertEquals(notification.deletedAt.getEpochSecond(), deletedAt.getEpochSecond());*/
    }

    @Test
    void deleteById() {
        notificationRepository.deleteById("1");
        Optional<Notification> byId = notificationRepository.findById("1");
        Assertions.assertFalse(byId.isPresent());
    }

}
