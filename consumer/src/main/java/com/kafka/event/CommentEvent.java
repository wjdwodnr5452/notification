package com.kafka.event;

import lombok.Data;

@Data
public class CommentEvent {
    private CommentEventType type; // 이벤트 종류
    private Long postId; // 게시글 id
    private Long userId; // 유저 id
    private Long commentId; // 댓글 id
}
