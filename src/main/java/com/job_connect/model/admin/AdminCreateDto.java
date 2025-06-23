package com.job_connect.model.admin;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminCreateDto {

    @NotBlank(message = "Please input full name")
    private String fullName;

    private String employeeId;

    @NotBlank(message = "Please input full name")
    private String email;

    private String phoneNumber;

    private String avt;

    @NotBlank(message = "Please select role")
    private String roleCode;

    private String orgId;

}
