package com.job_connect.controller.impl;

import com.job_connect.constant.ApiConstant;
import com.job_connect.controller.OrganizationController;
import com.job_connect.model.PageResponse;
import com.job_connect.model.organization.OrganizationCreateDto;
import com.job_connect.model.organization.OrganizationDto;
import com.job_connect.model.organization.OrganizationRequestDto;
import com.job_connect.model.organization.OrganizationUpdateDto;
import com.job_connect.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstant.ORGANIZATION_API)
public class OrganizationControllerImpl implements OrganizationController {

    private final OrganizationService organizationService;

    @Override
    public PageResponse<OrganizationDto> getOrganization(OrganizationRequestDto request) {
        return organizationService.getOrganizations(request);
    }

    @Override
    public OrganizationDto getOrganization(String id) {
        return organizationService.getOrganization(id);
    }

    @Override
    public OrganizationDto createOrganization(OrganizationCreateDto request) {
        return organizationService.createOrganization(request);
    }

    @Override
    public OrganizationDto updateOrganization(String id, OrganizationUpdateDto request) {
        return organizationService.updateOrganizationDto(id, request);
    }

    @Override
    public OrganizationDto activeOrganization(String id, int status) {
        return organizationService.activeOrganization(id, status);
    }


}
