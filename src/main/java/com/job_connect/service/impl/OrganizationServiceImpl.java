package com.job_connect.service.impl;

import com.job_connect.model.PageResponse;
import com.job_connect.model.organization.OrganizationCreateDto;
import com.job_connect.model.organization.OrganizationDto;
import com.job_connect.model.organization.OrganizationRequestDto;
import com.job_connect.model.organization.OrganizationUpdateDto;
import com.job_connect.repository.OrganizationRepository;
import com.job_connect.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Override
    public PageResponse<OrganizationDto> getOrganizations(OrganizationRequestDto request) {
        return null;
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
    public OrganizationDto updateOrganizationDto(String id, OrganizationUpdateDto request) {
        return null;
    }

    @Override
    public OrganizationDto activeOrganization(String id, boolean active) {
        return null;
    }
}
