package com.job_connect.model.organization;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class OrganizationDto {

    private String id;

    private String name;

    private String address;

    private String email;

    private String phone;

    private String website;

    private String policyUrl;

    private String termUrl;

    private String avatar;

    private String createdBy;

    private LocalDateTime createdAt;

    private String updatedBy;

    private LocalDateTime updatedAt;

}
