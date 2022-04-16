package com.millimetric.demo.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.millimetric.demo.redis.model.MemberPushNotification;
import com.millimetric.demo.redis.model.RedisPubsubMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MemberNotificationRedisSender {

    public static final String REDIS_CHANNEL_MEMBER_NOTIFICATION = "memberNotification";
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper mapper;


    public void pushNotificationToMember(MemberPushNotification memberPushNotification, Long memberId) {
        log.info("Sending data to user are {} and message going to id is {}",
                memberPushNotification.toString(), memberId
        );
        try {
            var data = mapper.writeValueAsString(RedisPubsubMessage.builder()
                    .user(memberId.toString())
                    .destination("/queue/notification")
                    .message(memberPushNotification)
                    .build());
            redisTemplate.convertAndSend(REDIS_CHANNEL_MEMBER_NOTIFICATION, data);
        } catch (JsonProcessingException e) {
            log.error("Error on converting data to json", e);
        }
    }
}
