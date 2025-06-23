package com.job_connect.model.admin;

import com.job_connect.model.organization.OrganizationDto;
import lombok.Data;

import java.time.Instant;

@Data
public class AdminDto {
    private String id;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String employeeId;

    private String avt;

    private OrganizationDto organization;

    private RoleDto role;

    private String createdBy;

    private Instant createdAt;

    private String updatedBy;

    private Instant updatedAt;
}
