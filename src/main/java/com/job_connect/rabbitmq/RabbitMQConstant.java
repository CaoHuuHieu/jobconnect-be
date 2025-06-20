package com.job_connect.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RabbitMQConstant {

    LOG("log_queue", "log_exchange", "log_routing_key");

    private final String queue;
    private final String exchange;
    private final String routingKey;

}
