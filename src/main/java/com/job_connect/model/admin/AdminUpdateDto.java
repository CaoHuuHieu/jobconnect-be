package com.job_connect.model.admin;


import lombok.Data;

@Data
public class AdminUpdateDto {

    private String fullName;

    private String email;

    private String phoneNumber;

    private String avt;

    private String roleCode;

    private String orgId;

}
