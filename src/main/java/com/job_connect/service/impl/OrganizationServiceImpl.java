package com.job_connect.service.impl;

import com.job_connect.entity.Admin;
import com.job_connect.entity.Organization;
import com.job_connect.entity.Organization_;
import com.job_connect.entity.Role;
import com.job_connect.exception.BusinessException;
import com.job_connect.exception.ForbiddenException;
import com.job_connect.exception.NotFoundException;
import com.job_connect.helper.AuthenticationHelper;
import com.job_connect.mapper.OrganizationMapper;
import com.job_connect.model.ErrorDetail;
import com.job_connect.model.ErrorResponse;
import com.job_connect.model.PageResponse;
import com.job_connect.model.organization.OrganizationCreateDto;
import com.job_connect.model.organization.OrganizationDto;
import com.job_connect.model.organization.OrganizationRequestDto;
import com.job_connect.model.organization.OrganizationUpdateDto;
import com.job_connect.repository.OrganizationRepository;
import com.job_connect.service.OrganizationService;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;

    @Override
    public PageResponse<OrganizationDto> getOrganizations(OrganizationRequestDto request) {
        Admin currentAdmin = AuthenticationHelper.getCurrentAdmin();
        Specification<Organization> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!currentAdmin.getRole().getCode().equals(Role.SUPER_ADMIN)) {
                predicates.add(cb.equal(root.get(Organization_.ID), currentAdmin.getOrganization().getId()));
            }
            if (StringUtils.isNotBlank(request.getQ()) && StringUtils.isNotBlank(request.getSearch())) {
                predicates.add(
                        switch (request.getQ()) {
                            case Organization_.ID -> cb.equal(root.get(Organization_.ID), request.getSearch());
                            case Organization_.EMAIL -> cb.equal(root.get(Organization_.EMAIL), request.getSearch());
                            case Organization_.NAME -> cb.equal(root.get(Organization_.NAME), request.getSearch());
                            case Organization_.PHONE -> cb.equal(root.get(Organization_.PHONE), request.getSearch());
                            default -> throw new IllegalStateException("Unexpected value: " + request.getQ());
                        });
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.Direction.valueOf(request.getSort()), request.getOrderBy());
        Page<Organization> page = organizationRepository.findAll(specification, pageable);
        PageResponse<OrganizationDto> response = PageResponse.toPageResponse(page, organizationMapper::toOrganizationDto);
        return response;
    }

    @Override
    public OrganizationDto getOrganization(String id) {
        Admin currentAdmin = AuthenticationHelper.getCurrentAdmin();

        if(!currentAdmin.getRole().getCode().equals(Role.SUPER_ADMIN)
                && !currentAdmin.getOrganization().getId().equals(id) )
                throw new ForbiddenException("You don't have permission to do this action!");

        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Organization doesn't found!"));

        return organizationMapper.toOrganizationDto(organization);
    }

    @Override
    @Transactional
    public OrganizationDto createOrganization(OrganizationCreateDto request) {
        Admin currentAdmin = AuthenticationHelper.getCurrentAdmin();
        if(!currentAdmin.getRole().getCode().equals(Role.SUPER_ADMIN))
            throw new ForbiddenException("You don't have permission to do this action!");

        Organization organization = organizationMapper.toOrganization(request);
        organization = organizationRepository.save(organization);
        return organizationMapper.toOrganizationDto(organization);
    }

    @Override
    @Transactional
    public OrganizationDto updateOrganizationDto(String id, OrganizationUpdateDto request) {
        Admin currentAdmin = AuthenticationHelper.getCurrentAdmin();
        if(!currentAdmin.getRole().getCode().equals(Role.SUPER_ADMIN)
                ||
            (currentAdmin.getRole().getCode().equals(Role.ORG_ADMIN) && !currentAdmin.getOrganization().getId().equals(id))
        ) {
            throw new ForbiddenException("You don't have permission to do this action!");
        }

        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Organization doesn't found"));
        if(request.getOrgLogo() != null)
            organization.setOrgLogo(request.getOrgLogo());
        if(request.getName() != null)
            organization.setName(request.getName());
        if(request.getEmail() != null)
            organization.setEmail(request.getEmail());
        if(request.getAddress() != null)
            organization.setAddress(request.getAddress());
        if(request.getWebsite() != null)
            organization.setWebsite(request.getWebsite());
        if(request.getTermsUrl() != null)
            organization.setTermsUrl(request.getTermsUrl());
        if(request.getPrivacyUrl() != null)
            organization.setPrivacyUrl(request.getPrivacyUrl());

        organization = organizationRepository.save(organization);
        return organizationMapper.toOrganizationDto(organization);
    }

    @Override
    @Transactional
    public OrganizationDto activeOrganization(String id, int status) {
        Admin currentAdmin = AuthenticationHelper.getCurrentAdmin();
        if(!currentAdmin.getRole().getCode().equals(Role.SUPER_ADMIN))
            throw new ForbiddenException("You don't have permission to do this action!");

        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find this organization!"));

        organization.setStatus(status);
        organizationRepository.save(organization);
        return organizationMapper.toOrganizationDto(organization);
    }

    @Override
    public void checkOrgCode(String orgCode) {
        Admin currentAdmin = AuthenticationHelper.getCurrentAdmin();
        if (!currentAdmin.getRole().getCode().equals(Role.SUPER_ADMIN))
            throw new ForbiddenException("You don't have permission to do this action!");
        Organization organization = organizationRepository.findByCode(orgCode);
        if (organization != null) {
            ErrorDetail errorDetail = ErrorDetail.builder()
                    .field("orgCode")
                    .issue("The Code is already existing. Please enter another code!")
                    .build();
            throw BusinessException.builder()
                    .code(400)
                    .message("The Code is already existing. Please enter another code!")
                    .errors(List.of(errorDetail))
                    .build();
        }
    }
}
