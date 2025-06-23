package com.job_connect.service.impl;

import com.job_connect.HibernateUtil;
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
import com.job_connect.rabbitmq.Message;
import com.job_connect.rabbitmq.RabbitMQConstant;
import com.job_connect.repository.AdminRepository;
import com.job_connect.repository.OrganizationRepository;
import com.job_connect.repository.RoleRepository;
import com.job_connect.service.AdminService;
import com.job_connect.service.RabbitMQService;
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

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final OrganizationRepository organizationRepository;
    private final RoleRepository roleRepository;
    private final AdminMapper adminMapper;
    private final RabbitMQService rabbitMQService;

    @Override
    public PageResponse<AdminDto> getAdmins(AdminRequestDto request) {
        Admin currentAdmin = AuthHelper.getCurrentAdmin();
        //only super admin and org_admin can perform this action
        if(!List.of(Role.SUPER_ADMIN, Role.ORG_ADMIN).contains(AuthHelper.getCurrentRole()))
            throw new ForbiddenException();

        Specification<Admin> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!currentAdmin.getRole().getCode().equals(Role.SUPER_ADMIN)) {
                predicates.add(cb.equal(root.get(Admin_.ORGANIZATION).get(Organization_.ID), currentAdmin.getOrganization().getId()));
            }
            if (StringUtils.isNotBlank(request.getSearchBy()) && StringUtils.isNotBlank(request.getSearchValue())) {
                predicates.add(
                        switch (request.getSearchBy()) {
                            case Admin_.ID -> cb.equal(root.get(Admin_.ID), request.getSearchValue());
                            case Admin_.EMAIL -> cb.like(cb.lower(root.get(Admin_.EMAIL)), HibernateUtil.startWith(request.getSearchValue()));
                            case Admin_.EMPLOYEE_ID -> cb.equal(root.get(Admin_.EMPLOYEE_ID), request.getSearchValue());
                            case Admin_.FULL_NAME -> cb.like(cb.lower(root.get(Admin_.FULL_NAME)), HibernateUtil.betweenWith(request.getSearchValue()));
                            default -> null;
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

        if(AuthHelper.getCurrentRole().equals(Role.HR_ADMIN)
                && id.equals(AuthHelper.getCurrentAdmin().getId())
        ) {
            throw new NotFoundException("Admin doesn't found");
        }

        return adminMapper.toAdminDto(admin);
    }

    @Override
    @Transactional
    public AdminDto createAdmin(AdminCreateDto request) {
        String currentRole = AuthHelper.getCurrentRole();

        //Only Super Admin and Org Admin can perform this action
        if(!(currentRole.equals(Role.SUPER_ADMIN) || currentRole.equals(Role.ORG_ADMIN))) {
            throw new ForbiddenException();
        }

        Organization organization = null;
        if(request.getRoleCode().equals(Role.SUPER_ADMIN)) {
            //Only SuperAdmin can create another SuperAdmin
            if(!currentRole.equals(Role.SUPER_ADMIN))
                throw new ForbiddenException();
        } else {
            //SuperAdmin can create admins for any org and OrgAmin only can create admins for their org!
            if(currentRole.equals(Role.SUPER_ADMIN))
                 organization = organizationRepository.findById(request.getOrgId())
                    .orElseThrow(() -> new NotFoundException("Organization doesn't found"));
            else
                organization = organizationRepository.findById(AuthHelper.getCurrentOrg())
                    .orElseThrow(() -> new NotFoundException("Organization doesn't found"));
        }

        Role role = roleRepository.findByCode(request.getRoleCode())
                .orElseThrow(() -> new NotFoundException("Role doesn't found"));

        Admin admin = Admin.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .organization(organization)
                .createdBy(AuthHelper.getCurrentAdmin().getId())
                .role(role)
                .avt(request.getAvt())
                .phoneNumber(request.getPhoneNumber())
                .build();
        adminRepository.save(admin);
        pushCreateAdminMessage(admin);
        return adminMapper.toAdminDto(admin);
    }

    private void pushCreateAdminMessage(Admin admin) {
        String msg = "Admin with id " + admin.getId() + " is created by "
                + (AuthHelper.getCurrentRole().equals(Role.SUPER_ADMIN) ? "SUPER_ADMIN " + admin.getId() : admin.getId());
        Message message = Message.builder()
                .org(admin.getOrganization().getId())
                .admin(AuthHelper.getCurrentAdmin().getId())
                .createdAt(Instant.now().toString())
                .msg(msg)
                .build();
        rabbitMQService.pushMessage(RabbitMQConstant.LOG, message);
    }

    @Override
    @Transactional
    public AdminDto updateAdmin(String id, AdminUpdateDto request) {
        String currentAdminRole = AuthHelper.getCurrentRole();

        //Only Super Admin and Org Admin can perform this action
        if(!(currentAdminRole.equals(Role.SUPER_ADMIN) || currentAdminRole.equals(Role.ORG_ADMIN))) {
            throw new ForbiddenException();
        }

        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Admin doesn't found"));

        Organization organization = null;
        if(request.getRoleCode().equals(Role.SUPER_ADMIN)) {
            //Only SuperAdmin can update an admin to SuperAdmin
            if(!currentAdminRole.equals(Role.SUPER_ADMIN))
                throw new ForbiddenException();
        } else {
            //Only super admins can change the organization for admins
            if(currentAdminRole.equals(Role.SUPER_ADMIN))
                organization = organizationRepository.findById(request.getOrgId())
                        .orElseThrow(() -> new NotFoundException("Organization doesn't found"));
        }

        Role role = roleRepository.findByCode(request.getRoleCode())
                .orElseThrow(() -> new NotFoundException("Role doesn't found"));

        admin.setOrganization(organization);
        admin.setRole(role);
        admin.setUpdatedBy(AuthHelper.getCurrentAdmin().getId());
        if(StringUtils.isNotBlank(request.getEmail()))
            admin.setEmail(request.getEmail());
        if(StringUtils.isNotBlank(request.getEmployeeId()))
            admin.setEmployeeId(request.getEmployeeId());
        if(StringUtils.isNotBlank(request.getFullName()))
            admin.setFullName(request.getFullName());
        if(StringUtils.isNotBlank(request.getPhoneNumber()))
            admin.setPhoneNumber(request.getPhoneNumber());
        if(StringUtils.isNotBlank(request.getAvt()))
            admin.setAvt(request.getAvt());

        adminRepository.save(admin);
        pushUpdateAdminMessage(admin);
        return adminMapper.toAdminDto(admin);

    }

    private void pushUpdateAdminMessage(Admin admin) {
        String msg = "Admin with id " + admin.getId() + " is updated by "
                + (AuthHelper.getCurrentRole().equals(Role.SUPER_ADMIN) ? "SUPER_ADMIN" : admin.getId());
        Message message = Message.builder()
                .org(admin.getOrganization().getId())
                .admin(AuthHelper.getCurrentAdmin().getId())
                .createdAt(Instant.now().toString())
                .msg(msg)
                .build();
        rabbitMQService.pushMessage(RabbitMQConstant.LOG, message);
    }

    @Override
    @Transactional
    public void deleteAdmin(String id) {
        adminRepository.deleteById(id);
    }

    @Override
    public AdminDto active(String id, int status) {

        return null;
    }

}
