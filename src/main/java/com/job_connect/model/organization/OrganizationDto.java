package com.job_connect.model.organization;

import lombok.Data;
import java.time.Instant;


@Data
public class OrganizationDto {

    private String id;

    private String name;

    private String orgLogo;

    private String website;

    private String orgCode;

    private Instant createdAt;

    private String email;

    private String address;

    private String termsUrl;

    private String privacyUrl;

    private Instant updatedAt;

    private String facebook;

    private String linkedIn;

    private int status;

}
