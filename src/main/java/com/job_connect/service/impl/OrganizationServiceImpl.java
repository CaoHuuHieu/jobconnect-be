package com.job_connect.service.impl;

import com.job_connect.util.HibernateUtil;
import com.job_connect.entity.Admin;
import com.job_connect.entity.Organization;
import com.job_connect.entity.Organization_;
import com.job_connect.entity.Role;
import com.job_connect.exception.BusinessException;
import com.job_connect.helper.AuthHelper;
import com.job_connect.mapper.OrganizationMapper;
import com.job_connect.model.PageResponse;
import com.job_connect.model.organization.OrganizationCreateDto;
import com.job_connect.model.organization.OrganizationDto;
import com.job_connect.model.organization.OrganizationRequestDto;
import com.job_connect.model.organization.OrganizationUpdateDto;
import com.job_connect.rabbitmq.Message;
import com.job_connect.rabbitmq.RabbitMQConstant;
import com.job_connect.repository.OrganizationRepository;
import com.job_connect.service.OrganizationService;
import com.job_connect.service.RabbitMQService;
import com.job_connect.util.ObjectMapperUtil;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;
    private final RabbitMQService rabbitMQService;

    @Override
    public PageResponse<OrganizationDto> getOrganizations(OrganizationRequestDto request) {
        Admin currentAdmin = AuthHelper.getCurrentAdmin();
        Specification<Organization> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!currentAdmin.getRole().getCode().equals(Role.SUPER_ADMIN)) {
                predicates.add(cb.equal(root.get(Organization_.ID), currentAdmin.getOrganization().getId()));
            }
            if (StringUtils.isNotBlank(request.getSearchBy()) && StringUtils.isNotBlank(request.getSearchValue())) {
                predicates.add(
                        switch (request.getSearchBy()) {
                            case Organization_.ID -> cb.equal(root.get(Organization_.ID), request.getSearchValue());
                            case Organization_.NAME -> cb.like(cb.lower(root.get(Organization_.NAME)),
                                    HibernateUtil.betweenWith(request.getSearchValue()));
                            case Organization_.ORG_CODE -> cb.equal(root.get(Organization_.ORG_CODE), request.getSearchValue());
                            default -> null;
                        });
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.Direction.valueOf(request.getSort()), request.getOrderBy());
        Page<Organization> page = organizationRepository.findAll(specification, pageable);
        return PageResponse.toPageResponse(page, organizationMapper::toOrganizationDto);
    }

    @Override
    public OrganizationDto getOrganization(String id) {
        Admin currentAdmin = AuthHelper.getCurrentAdmin();

        if(!currentAdmin.getRole().getCode().equals(Role.SUPER_ADMIN)
                && !currentAdmin.getOrganization().getId().equals(id) )
                throw new BusinessException(HttpStatus.FORBIDDEN);

        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND));

        return organizationMapper.toOrganizationDto(organization);
    }

    @Override
    @Transactional
    public OrganizationDto createOrganization(OrganizationCreateDto request) {
        Admin currentAdmin = AuthHelper.getCurrentAdmin();
        if(!currentAdmin.getRole().getCode().equals(Role.SUPER_ADMIN))
            throw new BusinessException(HttpStatus.FORBIDDEN);

        Organization organization = organizationMapper.toOrganization(request);
        organization = organizationRepository.save(organization);
        pushCreateOrganizationMessage(organization);
        return organizationMapper.toOrganizationDto(organization);
    }

    private void pushCreateOrganizationMessage(Organization org) {
        Message message = Message.builder()
                .admin(AuthHelper.getCurrentAdmin().getId())
                .org(AuthHelper.getCurrentOrg())
                .createdAt(Instant.now().toString())
                .msg(org.getName() + " with id: " + org.getId() + " is created by " + AuthHelper.getCurrentAdmin().getId())
                .build();

        rabbitMQService.pushMessage(RabbitMQConstant.LOG, ObjectMapperUtil.toJson(message));
    }

    @Override
    @Transactional
    public OrganizationDto updateOrganizationDto(String id, OrganizationUpdateDto request) {
        Admin currentAdmin = AuthHelper.getCurrentAdmin();
        if(!currentAdmin.getRole().getCode().equals(Role.SUPER_ADMIN)
                ||
            (currentAdmin.getRole().getCode().equals(Role.ORG_ADMIN) && !currentAdmin.getOrganization().getId().equals(id))
        ) {
            throw new BusinessException(HttpStatus.FORBIDDEN);
        }

        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND));
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
        if(request.getFacebook() != null)
            organization.setFacebook(request.getFacebook());
        if(request.getLinkedIn() != null)
            organization.setLinkedIn(request.getLinkedIn());

        organization = organizationRepository.save(organization);
        pushUpdateOrganizationMessage(organization);
        return organizationMapper.toOrganizationDto(organization);
    }

    private void pushUpdateOrganizationMessage(Organization org) {
        Message message = Message.builder()
                .admin(AuthHelper.getCurrentAdmin().getId())
                .org(org.getId())
                .createdAt(Instant.now().toString())
                .msg(org.getName() + " with id: " + org.getId() + " is updated by " + AuthHelper.getCurrentAdmin().getId())
                .build();

        rabbitMQService.pushMessage(RabbitMQConstant.LOG, ObjectMapperUtil.toJson(message));
    }

    @Override
    @Transactional
    public OrganizationDto activeOrganization(String id, int status) {
        Admin currentAdmin = AuthHelper.getCurrentAdmin();
        if(!currentAdmin.getRole().getCode().equals(Role.SUPER_ADMIN))
            throw new BusinessException(HttpStatus.FORBIDDEN);

        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND));

        organization.setStatus(status);
        organizationRepository.save(organization);
        return organizationMapper.toOrganizationDto(organization);
    }

    @Override
    public void checkOrgCode(String orgCode) {
        Admin currentAdmin = AuthHelper.getCurrentAdmin();
        if (!currentAdmin.getRole().getCode().equals(Role.SUPER_ADMIN))
            throw new BusinessException(HttpStatus.FORBIDDEN);
        Organization organization = organizationRepository.findByOrgCode(orgCode);
        if (organization != null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST);
        }
    }
}
