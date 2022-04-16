package com.millimetric.demo.redis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RedisPubsubMessage {
    private String user;
    private String destination;
    private Object message;

}
