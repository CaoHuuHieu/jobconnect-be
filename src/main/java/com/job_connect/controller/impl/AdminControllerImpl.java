package com.job_connect.controller.impl;

import com.job_connect.constant.ApiConstant;
import com.job_connect.controller.AdminController;
import com.job_connect.model.PageResponse;
import com.job_connect.model.admin.AdminCreateDto;
import com.job_connect.model.admin.AdminDto;
import com.job_connect.model.admin.AdminRequestDto;
import com.job_connect.model.admin.AdminUpdateDto;
import com.job_connect.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstant.ADMIN_API)
@RequiredArgsConstructor
public class AdminControllerImpl implements AdminController {

    private final AdminService adminService;

    @Override
    public PageResponse<AdminDto> getAdmins(AdminRequestDto request) {
        return adminService.getAdmins(request);
    }

    @Override
    public AdminDto getAdmin(String id) {
        return adminService.getAdmin(id);
    }

    @Override
    public AdminDto createAdmin(AdminCreateDto request) {
        return adminService.createAdmin(request);
    }

    @Override
    public AdminDto updateAdmin(String id, AdminUpdateDto request) {
        return adminService.updateAdmin(id, request);
    }

    @Override
    public AdminDto activeAdmin(String id, int status) {
        return adminService.active(id, status);
    }
}
