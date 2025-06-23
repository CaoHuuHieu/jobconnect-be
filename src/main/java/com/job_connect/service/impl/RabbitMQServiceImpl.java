package com.job_connect.service.impl;

import com.job_connect.rabbitmq.Message;
import com.job_connect.rabbitmq.RabbitMQConstant;
import com.job_connect.service.RabbitMQService;
import com.job_connect.util.ObjectMapperUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitMQServiceImpl implements RabbitMQService {

    private final RabbitTemplate rabbitTemplate;
    private final ExecutorService rabbitExecutor;

    @PostConstruct
    public void init() {
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnsCallback(returned -> {
            log.error("Message not routed. exchange={}, key={}, reason={}",
                    returned.getExchange(), returned.getRoutingKey(), returned.getReplyText());
        });
    }

    @Override
    public void pushMessage(RabbitMQConstant queue, String msg) {
        rabbitExecutor.execute(() -> {
                log.info("Push message to queue {} and exchange {}", queue.getQueue(), queue.getExchange());
                rabbitTemplate.convertAndSend(queue.getExchange(), queue.getRoutingKey(), msg);
            }
        );
    }

    @Override
    public void pushMessage(RabbitMQConstant queue, Message msg) {
        rabbitExecutor.execute(() -> {
                    log.info("Push message to queue {} and exchange {}", queue.getQueue(), queue.getExchange());
                    rabbitTemplate.convertAndSend(queue.getExchange(), queue.getRoutingKey(), ObjectMapperUtil.toJson(msg));
                }
        );
    }

}
