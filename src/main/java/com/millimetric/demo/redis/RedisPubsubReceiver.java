package com.millimetric.demo.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.millimetric.demo.redis.model.RedisPubsubMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisPubsubReceiver {

    private final SimpMessagingTemplate template;
    private final ObjectMapper mapper;

    @SuppressWarnings("unused")
    public void receiveMessage(String message) {
        RedisPubsubMessage pubsubMessage;
        try {
            pubsubMessage = mapper.readValue(message, RedisPubsubMessage.class);
        } catch (JsonProcessingException e) {
            log.error("Error on converting json to websocket message", e);
            return;
        }
        log.info("Sending data to the topic {} and message {}",
                pubsubMessage.getDestination(), pubsubMessage.getMessage()
        );
        template.convertAndSendToUser(
                pubsubMessage.getUser(),
                pubsubMessage.getDestination(),
                pubsubMessage.getMessage()
        );
    }

}
