package com.job_connect.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.job_connect.rabbitmq.Message;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectMapperUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static<T> String toJson(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("Cannot convert object to json!");
            return null;
        }
    }

    public static<T> T toObject(String msg, Class<T> clazz) {
        try {
            return objectMapper.readValue(msg, clazz);
        } catch (Exception e) {
            log.error("Cannot convert json to object!");
            return null;
        }

    }
}
