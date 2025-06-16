package com.job_connect.mapper;


import com.job_connect.entity.Admin;
import com.job_connect.model.admin.AdminDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    AdminDto toAdminDto(Admin admin);
}
