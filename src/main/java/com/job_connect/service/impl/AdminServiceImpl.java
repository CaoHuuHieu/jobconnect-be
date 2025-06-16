package com.job_connect.service.impl;

import com.job_connect.entity.*;
import com.job_connect.exception.ForbiddenException;
import com.job_connect.exception.NotFoundException;
import com.job_connect.helper.AuthHelper;
import com.job_connect.mapper.AdminMapper;
import com.job_connect.model.PageResponse;
import com.job_connect.model.admin.AdminCreateDto;
import com.job_connect.model.admin.AdminDto;
import com.job_connect.model.admin.AdminRequestDto;
import com.job_connect.model.admin.AdminUpdateDto;
import com.job_connect.repository.AdminRepository;
import com.job_connect.repository.OrganizationRepository;
import com.job_connect.repository.RoleRepository;
import com.job_connect.service.AdminService;
import jakarta.persistence.criteria.Predicate;
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
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final OrganizationRepository organizationRepository;
    private final RoleRepository roleRepository;
    private final AdminMapper adminMapper;

    @Override
    public PageResponse<AdminDto> getAdmins(AdminRequestDto request) {
        Admin currentAdmin = AuthHelper.getCurrentAdmin();
        Specification<Admin> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!currentAdmin.getRole().getCode().equals(Role.SUPER_ADMIN)) {
                predicates.add(cb.equal(root.get(Admin_.ORGANIZATION).get(Organization_.ID), currentAdmin.getOrganization().getId()));
            }
            if (StringUtils.isNotBlank(request.getQ()) && StringUtils.isNotBlank(request.getSearch())) {
                predicates.add(
                        switch (request.getQ()) {
                            case Admin_.ID -> cb.equal(root.get(Admin_.ID), request.getSearch());
                            case Admin_.EMAIL -> cb.equal(root.get(Admin_.EMAIL), request.getSearch());
                            case Admin_.FULL_NAME -> cb.equal(root.get(Admin_.FULL_NAME), request.getSearch());
                            case Admin_.PHONE_NUMBER -> cb.equal(root.get(Admin_.PHONE_NUMBER), request.getSearch());
                            default -> throw new IllegalStateException("Unexpected value: " + request.getQ());
                        });
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.Direction.valueOf(request.getSort()), request.getOrderBy());
        Page<Admin> page = adminRepository.findAll(specification, pageable);
        return PageResponse.toPageResponse(page, adminMapper::toAdminDto);
    }

    @Override
    public AdminDto getAdmin(String id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Admin doesn't found"));

        if(AuthHelper.getCurrentRole().equals(Role.ORG_ADMIN)) {
            if(!admin.getOrganization().getId().equals(AuthHelper.getCurrentOrg()))
                throw new NotFoundException("Admin doesn't found");
        }

        if(AuthHelper.getCurrentRole().equals(Role.HR_ADMIN) && id.equals(AuthHelper.getCurrentAdmin().getId()))
            throw new NotFoundException("Admin doesn't found");

        return adminMapper.toAdminDto(admin);
    }

    @Override
    public AdminDto createAdmin(AdminCreateDto request) {
        String currentRole = AuthHelper.getCurrentRole();
        Organization organization = null;
        if(request.getRoleCode().equals(Role.SUPER_ADMIN)) {
            //Only SuperAdmin can create another SuperAdmin
            if(!currentRole.equals(Role.SUPER_ADMIN))
                throw new ForbiddenException();
        } else {
            organization = organizationRepository.findById(AuthHelper.getCurrentOrg())
                    .orElseThrow(() -> new NotFoundException("Organization doesn't found"));
        }

        Role role = roleRepository.findByCode(request.getRoleCode())
                .orElseThrow(() -> new NotFoundException("Role doesn't found"));

        if(!(currentRole.equals(Role.SUPER_ADMIN) || currentRole.equals(Role.ORG_ADMIN))) {
            throw new ForbiddenException();
        }
        Admin admin = Admin.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .organization(organization)
                .role(role)
                .avt(request.getAvt())
                .phoneNumber(request.getPhoneNumber())
                .build();
        adminRepository.save(admin);
        return adminMapper.toAdminDto(admin);
    }

    @Override
    public AdminDto updateAdmin(String id, AdminUpdateDto request) {
        String currentRole = AuthHelper.getCurrentRole();
        String orgId = currentRole.equals(Role.SUPER_ADMIN) ? request.getOrgId() : AuthHelper.getCurrentOrg();
        Organization organization = organizationRepository.findById(orgId)
                .orElseThrow(() -> new NotFoundException("Organization doesn't found"));

        Role role = roleRepository.findByCode(request.getRoleCode())
                .orElseThrow(() -> new NotFoundException("Role doesn't found"));

        if(currentRole.equals(Role.SUPER_ADMIN) || currentRole.equals(Role.ORG_ADMIN)) {
            Admin admin = Admin.builder()
                    .fullName(request.getFullName())
                    .email(request.getEmail())
                    .organization(organization)
                    .role(role)
                    .avt(request.getAvt())
                    .phoneNumber(request.getPhoneNumber())
                    .build();
            adminRepository.save(admin);
            return adminMapper.toAdminDto(admin);
        } else {
            throw new ForbiddenException();
        }
    }

    @Override
    public void deleteAdmin(String id) {

    }
}
