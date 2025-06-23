package com.job_connect.service;

import com.job_connect.rabbitmq.Message;
import com.job_connect.rabbitmq.RabbitMQConstant;

public interface RabbitMQService {

    void pushMessage(RabbitMQConstant queue, String msg);

    void pushMessage(RabbitMQConstant queue, Message msg);

}
