package com.kafka.event;

import lombok.Data;

import java.time.Instant;

@Data
public class FollowEvent {
    private FollowEventType type; // 이벤트 종류
    private Long userId; // 유저 id
    private Long targetUserId; // 누구를 팔로우 했는지
    private Instant createdAt; //언제 팔로우 눌렀는지
}
