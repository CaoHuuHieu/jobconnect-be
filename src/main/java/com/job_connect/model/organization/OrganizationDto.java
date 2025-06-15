package com.job_connect.model.organization;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdAt;

    private String updatedBy;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime updatedAt;

    private int status;

}
