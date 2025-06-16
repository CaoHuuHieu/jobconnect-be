package com.job_connect.model.organization;

import lombok.Data;


@Data
public class OrganizationUpdateDto {

    private String name;

    private String orgLogo;

    private String website;

    private String email;

    private String address;

    private String termsUrl;

    private String privacyUrl;

    private String facebook;

    private String linkedIn;

    private int status;
}
