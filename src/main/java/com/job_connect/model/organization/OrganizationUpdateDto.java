package com.job_connect.model.organization;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class OrganizationUpdateDto {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Please select a picture")
    private String orgLogo;

    @NotBlank(message = "Website cannot be blank")
    private String website;

    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    private String termsUrl;

    private String privacyUrl;

    private String facebook;

    private String linkedIn;

    private int status;
}
