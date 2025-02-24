package com.kafka.consumer.event;

import lombok.Data;

import java.time.Instant;

@Data
public class LikeEvent {
    private LikeEventType type; // 이벤트 종류
    private Long postId; // 게시글 id
    private Long userId; // 유저 id
    private Instant createdAt; //언제 좋아요 눌렀는지
}
