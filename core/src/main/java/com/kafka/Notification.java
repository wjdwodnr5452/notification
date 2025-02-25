package com.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;


@Getter
@AllArgsConstructor
@Document("notifications")
public abstract class Notification {
    private String id;
    private Long userId;
    private NotificationType type;
    private Instant occurredAt; // 알림 대상인 실제 이벤트가 발생한 시간
    private Instant createdAt;
    private Instant lastUpdatedAt;
    private Instant deletedAt; // 알림이 삭제될 시간

}


