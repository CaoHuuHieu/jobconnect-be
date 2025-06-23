package com.job_connect.controller;

import com.job_connect.model.PageResponse;
import com.job_connect.model.admin.AdminCreateDto;
import com.job_connect.model.admin.AdminDto;
import com.job_connect.model.admin.AdminRequestDto;
import com.job_connect.model.admin.AdminUpdateDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

public interface AdminController {

    @GetMapping()
    PageResponse<AdminDto> getAdmins(@ModelAttribute AdminRequestDto request);

    @GetMapping("/{id}")
    AdminDto getAdmin(@PathVariable String id);

    @PostMapping
    AdminDto createAdmin(@RequestBody @Valid AdminCreateDto request);

    @PutMapping("/{id}")
    AdminDto updateAdmin(@PathVariable String id, @RequestBody AdminUpdateDto request);

    @PutMapping("/{id}/{status}")
    AdminDto activeAdmin(@PathVariable String id, @PathVariable int status);

}
