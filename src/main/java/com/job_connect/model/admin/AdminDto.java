package com.job_connect.model.admin;

import com.job_connect.entity.Role;
import com.job_connect.model.organization.OrganizationDto;
import lombok.Data;

import java.time.Instant;

@Data
public class AdminDto {
    private String id;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String avt;

    private OrganizationDto organization;

    private Role role;

    private String createdBy;

    private Instant createdAt;

    private String updatedBy;

    private Instant updatedAt;
}
