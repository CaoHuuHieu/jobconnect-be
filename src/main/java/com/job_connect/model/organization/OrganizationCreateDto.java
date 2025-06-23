package com.job_connect.model.organization;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrganizationCreateDto {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Please select a picture")
    private String orgLogo;

    @NotBlank(message = "Website cannot be blank")
    private String website;

    @NotBlank(message = "Organization code cannot be blank")
    @Size(min = 3, max = 10, message = "Organization code must be between 3 and 10 characters")
    private String orgCode;

    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Email cannot be blank")
    private String address;

    private String termsUrl;

    private String privacyUrl;

    private String facebook;

    private String linkedIn;

    private int status;

}
