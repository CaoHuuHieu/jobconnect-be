package com.job_connect.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String admin;
    private String org;
    private String createdAt;
    private String msg;

    public Instant getCreatedAt() {
        return createdAt == null ? Instant.now() : Instant.parse(createdAt);
    }
}
