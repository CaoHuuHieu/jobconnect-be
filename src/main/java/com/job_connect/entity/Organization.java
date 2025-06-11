package com.job_connect.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "organizations")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Organization {

    @Id
    private String id;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private String website;

    @Column(name = "policy_url")
    private String policyUrl;

    @Column(name = "term_url")
    private String termUrl;

    @Column
    private String avatar;

    @Column
    private String createdBy;

    @Column
    private LocalDateTime createdAt;

    @Column
    private String updatedBy;

    @Column
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.id == null)
            this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
