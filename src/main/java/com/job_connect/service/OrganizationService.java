package com.job_connect.service;

import com.job_connect.model.organization.OrganizationCreateDto;
import com.job_connect.model.organization.OrganizationDto;
import com.job_connect.model.PageResponse;
import com.job_connect.model.organization.OrganizationRequestDto;
import com.job_connect.model.organization.OrganizationUpdateDto;

public interface OrganizationService {

    PageResponse<OrganizationDto> getOrganizations(OrganizationRequestDto request);

    OrganizationDto getOrganization(String id);

    OrganizationDto createOrganization(OrganizationCreateDto request);

    OrganizationDto updateOrganizationDto(String id, OrganizationUpdateDto request);

    OrganizationDto activeOrganization(String id, int status);

    void checkOrgCode(String orgCode);
}
