package com.job_connect.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "admins")
@Builder
public class Admin {
    @Id
    private String id;

    @Column
    private String fullName;

    @Column
    private String email;

    @Column
    private String phoneNumber;

    @Column
    private String avt;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @ManyToOne()
    @JoinColumn(name = "role_id")
    private Role role;

    @Column
    private String createdBy;

    @Column
    @CreationTimestamp
    private Instant createdAt;

    @Column
    private String updatedBy;

    @Column
    @UpdateTimestamp
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.id == null)
            this.id = UUID.randomUUID().toString();
    }
}
