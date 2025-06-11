package com.job_connect.model.organization;

import lombok.Data;

@Data
public class OrganizationCreateDto {

    private String name;

    private String email;

    private String address;

    private String phone;

    private String website;

    private String policyUrl;

    private String termUrl;

    private String avatar;

}
