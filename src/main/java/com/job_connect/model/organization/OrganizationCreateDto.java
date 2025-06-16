package com.job_connect.model.organization;

import lombok.Data;

@Data
public class OrganizationCreateDto {

    private String name;

    private String orgLogo;

    private String website;

    private String orgCode;

    private String email;

    private String address;

    private String termsUrl;

    private String privacyUrl;

    private String facebook;

    private String linkedIn;

    private int status;

}
