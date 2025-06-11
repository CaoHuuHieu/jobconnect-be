package com.job_connect.mapper;

import com.job_connect.entity.Organization;
import com.job_connect.model.organization.OrganizationCreateDto;
import com.job_connect.model.organization.OrganizationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {

    OrganizationDto toOrganizationDto(Organization organization);

    Organization toOrganization(OrganizationCreateDto request);
}
