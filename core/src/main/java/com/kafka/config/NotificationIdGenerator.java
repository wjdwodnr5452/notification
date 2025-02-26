package com.kafka.config;

import org.bson.types.ObjectId;


/**
 * 알림 식별자 (고유한 id 생성)
 */
public class NotificationIdGenerator {

    public static String generate() {
        return new ObjectId().toString();
    }
}
