package com.job_connect.controller;

import com.job_connect.model.admin.RoleDto;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface RoleController {

    @GetMapping()
    List<RoleDto> getRoles();

}
