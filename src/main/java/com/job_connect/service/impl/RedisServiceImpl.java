package com.job_connect.service.impl;

import com.job_connect.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final StringRedisTemplate redisTemplate;


    @Override
    public String getValue(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        }catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public void store(String key, String value) {
        try {
            log.debug("Store {}: {} ", key, value);
            redisTemplate.opsForValue().set(key, value);
        }catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
