package com.kafka;

import com.kafka.repository.impl.NotificationRepositoryMemoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class NotificationRepositoryMemoryImplTest {


    private final NotificationRepositoryMemoryImpl nrm = new NotificationRepositoryMemoryImpl();

    private final Instant now = Instant.now();
    private final Instant deletedAt = Instant.now().plus(90, ChronoUnit.DAYS);

    @BeforeEach
    public void save() {
        Notification saveNotification = new Notification("1", 2L, NotificationType.LIke, now, deletedAt);
        nrm.save(saveNotification);
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
        Notification notification = nrm.findById("1").orElseThrow();
        Assertions.assertEquals(notification.id, "1");
        Assertions.assertEquals(notification.userId, 2L);
        Assertions.assertEquals(notification.createdAt, now);
        Assertions.assertEquals(notification.deletedAt, deletedAt);
    }

    @Test
    void deleteById() {
        nrm.deleteById("1");
        Optional<Notification> byId = nrm.findById("1");
        Assertions.assertFalse(byId.isPresent());
    }

}
