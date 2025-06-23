package com.job_connect.mapper;

import com.job_connect.entity.Role;
import com.job_connect.model.admin.RoleDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDto toDto(Role role);

}
