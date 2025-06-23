package com.job_connect.service.impl;

import com.job_connect.entity.Role;
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
        return roles.stream().map(roleMapper::toDto).toList();
    }

}
