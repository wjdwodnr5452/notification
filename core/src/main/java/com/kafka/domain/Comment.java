package com.kafka.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;


@Getter
@AllArgsConstructor
public class Comment {
    private Long id;
    private Long userId;
    private String content;
    private Instant createdAt;
}
