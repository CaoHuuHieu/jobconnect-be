package com.job_connect.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "system_logs")
public class SystemLog {

    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @OneToOne
    @JoinColumn(name = "org_id")
    private Organization organization;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "msg")
    private String msg;

    @PrePersist
    public void initId() {
        this.id = UUID.randomUUID().toString();
    }

}
