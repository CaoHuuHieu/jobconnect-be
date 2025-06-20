package com.job_connect.config;

import com.job_connect.rabbitmq.RabbitMQConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    Queue logQueue() {
        return new Queue(RabbitMQConstant.LOG.getQueue());
    }

    @Bean
    TopicExchange logExchange() {
        return new TopicExchange(RabbitMQConstant.LOG.getExchange());
    }

    @Bean
    Binding bindingLog() {
        return BindingBuilder.bind(logQueue()).to(logExchange()).with(RabbitMQConstant.LOG.getRoutingKey());
    }

}
