package com.job_connect.controller.impl;

import com.job_connect.constant.ApiConstant;
import com.job_connect.controller.OrganizationController;
import com.job_connect.entity.Organization;
import com.job_connect.model.PageResponse;
import com.job_connect.model.organization.OrganizationCreateDto;
import com.job_connect.model.organization.OrganizationDto;
import com.job_connect.model.organization.OrganizationRequestDto;
import com.job_connect.model.organization.OrganizationUpdateDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(ApiConstant.ORGANIZATION_API)
public class OrganizationControllerImpl implements OrganizationController {

    @Override
    public PageResponse<Organization> getOrganization(OrganizationRequestDto request) {
        PageResponse<Organization> page =  new PageResponse<>();
        page.setPage(1);
        return page;
    }

    @Override
    public OrganizationDto getOrganization(String id) {
        return null;
    }

    @Override
    public OrganizationDto createOrganization(OrganizationCreateDto request) {
        return null;
    }

    @Override
    public OrganizationDto updateOrganization(String id, OrganizationUpdateDto request) {
        return null;
    }


}
