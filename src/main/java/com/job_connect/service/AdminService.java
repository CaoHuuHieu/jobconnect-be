package com.job_connect.service;

import com.job_connect.model.PageResponse;
import com.job_connect.model.admin.AdminCreateDto;
import com.job_connect.model.admin.AdminDto;
import com.job_connect.model.admin.AdminRequestDto;
import com.job_connect.model.admin.AdminUpdateDto;

public interface AdminService {

    PageResponse<AdminDto> getAdmins(AdminRequestDto request);

    AdminDto getAdmin(String id);

    AdminDto createAdmin(AdminCreateDto request);

    AdminDto updateAdmin(String id, AdminUpdateDto request);

    void deleteAdmin(String id);

}
