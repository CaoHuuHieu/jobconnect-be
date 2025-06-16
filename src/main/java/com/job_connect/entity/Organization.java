package com.job_connect.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "organizations")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Organization {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "org_logo")
    private String orgLogo;

    @Column(name = "website")
    private String website;

    @Column(name = "org_code")
    private String orgCode;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "terms_url")
    private String termsUrl;

    @Column(name = "privacy_url")
    private String privacyUrl;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

    @Column(name = "facebook")
    private String facebook;

    @Column(name = "linked_in")
    private String linkedIn;

    @Column(name = "status")
    private Integer status;

    @PrePersist
    public void prePersist() {
        if (this.id == null)
            this.id = UUID.randomUUID().toString();
    }

}
