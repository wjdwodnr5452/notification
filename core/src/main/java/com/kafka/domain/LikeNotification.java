package com.kafka.domain;

import lombok.Getter;
import org.springframework.data.annotation.TypeAlias;

import java.time.Instant;
import java.util.List;

@Getter
@TypeAlias("LikeNotification") // TypeAlias 몽고db에 저장하고 다시 자바객체에 어디에 저장할 건지
public class LikeNotification extends Notification {

    private final Long postId;
    private final List<Long> likerIds;


    public LikeNotification(String id,
                            Long userId,
                            NotificationType type,
                            Instant occurredAt,
                            Instant createdAt,
                            Instant lastUpdatedAt,
                            Instant deletedAt, Long postId, List<Long> likerIds) {
        super(id, userId, type, occurredAt, createdAt, lastUpdatedAt, deletedAt);

        this.postId = postId;
        this.likerIds = likerIds;
    }

    // 좋아요 추가
    public void addLikers(Long likerId, Instant occurredAt, Instant now, Instant retention){
        this.likerIds.add(likerId);
        this.setOccurredAt(occurredAt);
        this.setLastUpdatedAt(now);
        this.setDeletedAt(retention);
    }

    // 좋아요 삭제
    public void removeLikers(Long userId, Instant now){
        this.likerIds.remove(userId);
        this.setLastUpdatedAt(now);
    }

}
