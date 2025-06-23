package com.job_connect.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectMapperUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);;

    public static<T> String toJson(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("[ObjectMapperUtil][toJson] ERROR {}", e.getMessage());
            return null;
        }
    }

    public static<T> T toObject(String msg, Class<T> clazz) {
        try {
            return objectMapper.readValue(msg, clazz);
        } catch (Exception e) {
            log.error("[ObjectMapperUtil][toObject] ERROR {}", e.getMessage());
            return null;
        }

    }
}
