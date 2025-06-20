package com.job_connect.service;

import com.job_connect.model.PageResponse;
import com.job_connect.model.log.LogRequestDto;
import com.job_connect.model.log.SystemLogDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public interface LogService {

    PageResponse<SystemLogDto> getSystemLog(LogRequestDto request);

    @RabbitListener(queues = "log_queue")
    void saveLog(String msg);

}
