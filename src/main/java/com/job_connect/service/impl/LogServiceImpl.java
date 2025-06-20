package com.job_connect.service.impl;

import com.job_connect.entity.Admin;
import com.job_connect.entity.Organization;
import com.job_connect.entity.SystemLog;
import com.job_connect.model.PageResponse;
import com.job_connect.model.log.LogRequestDto;
import com.job_connect.model.log.SystemLogDto;
import com.job_connect.rabbitmq.Message;
import com.job_connect.repository.AdminRepository;
import com.job_connect.repository.OrganizationRepository;
import com.job_connect.repository.SystemLogRepository;
import com.job_connect.service.LogService;
import com.job_connect.util.ObjectMapperUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final SystemLogRepository systemLogRepository;
    private final OrganizationRepository organizationRepository;
    private final AdminRepository adminRepository;

    @Override
    public PageResponse<SystemLogDto> getSystemLog(LogRequestDto request) {
        return null;
    }

    @Override
    @Transactional
    public void saveLog(String msg) {
        log.debug("Received message from rabbitmq: {}", msg);
        Message message = ObjectMapperUtil.toObject(msg, Message.class);
        if(message == null){
            log.error("Cannot create System log because message is null!");
            return;
        }

        String errorMsg = null;
        Organization organization = null;
        if(message.getOrg() != null) {
            organization = organizationRepository.findById(message.getOrg()).orElse(null);
            if(organization == null)
                errorMsg = "Cannot find the organization with id " + message.getOrg() + " to create new log system: " + msg;
        }

        Admin admin = null;
        if(message.getAdmin() != null) {
            admin = adminRepository.findById(message.getAdmin()).orElse(null);
            if(admin == null)
                errorMsg = "Cannot find the admin with id " + message.getAdmin() + " to create new log system: " + msg;
        }

        SystemLog systemLog = SystemLog.builder()
                .admin(admin)
                .organization(organization)
                .createdAt(message.getCreatedAt())
                .msg(errorMsg != null ? errorMsg : message.getMsg())
                .build();

        systemLogRepository.save(systemLog);
    }
}

