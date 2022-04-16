package com.millimetric.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.millimetric.demo.redis.MemberNotificationRedisSender;
import com.millimetric.demo.redis.RedisPubsubReceiver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
@RequiredArgsConstructor
public class RedisPubsubConfiguration {

    private final SimpMessagingTemplate template;
    private final ObjectMapper mapper;
    private final RedisConnectionFactory connectionFactory;

    @Bean
    public RedisPubsubReceiver redisPubSubReceiver() {
        return new RedisPubsubReceiver(template, mapper);
    }

    @Bean
    public MessageListenerAdapter redisPubsubListenerAdapter() {
        return new MessageListenerAdapter(redisPubSubReceiver(), "receiveMessage");
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(
                redisPubsubListenerAdapter(),
                new PatternTopic(MemberNotificationRedisSender.REDIS_CHANNEL_MEMBER_NOTIFICATION)
        );
        return container;
    }

}
