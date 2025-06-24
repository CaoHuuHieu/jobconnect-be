package com.job_connect.service.impl;

import com.job_connect.entity.Role;
import com.job_connect.helper.AuthHelper;
import com.job_connect.mapper.RoleMapper;
import com.job_connect.model.admin.RoleDto;
import com.job_connect.repository.RoleRepository;
import com.job_connect.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public List<RoleDto> getRoles() {
        List<Role> roles = roleRepository.findAll();
        if(!AuthHelper.getCurrentRole().equals(Role.SUPER_ADMIN))
            roles.removeIf(role -> role.getCode().equals(Role.SUPER_ADMIN));
        return roles.stream().map(roleMapper::toDto).toList();
    }

}
