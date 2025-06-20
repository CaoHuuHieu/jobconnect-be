package com.job_connect.service;

public interface RedisService {

    String getValue(String key);

    void store(String key, String value);

}
