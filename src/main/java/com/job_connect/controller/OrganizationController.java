package com.job_connect.controller;

import com.job_connect.model.PageResponse;
import com.job_connect.model.organization.OrganizationCreateDto;
import com.job_connect.model.organization.OrganizationDto;
import com.job_connect.model.organization.OrganizationRequestDto;
import com.job_connect.model.organization.OrganizationUpdateDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public interface OrganizationController {

    @GetMapping()
    PageResponse<OrganizationDto> getOrganization(@ModelAttribute OrganizationRequestDto request);

    @GetMapping("/{id}")
    OrganizationDto getOrganization(@PathVariable String id);

    @PostMapping
    OrganizationDto createOrganization(@RequestBody OrganizationCreateDto request);

    @PutMapping("/{id}")
    OrganizationDto updateOrganization(@PathVariable String id, @RequestBody OrganizationUpdateDto request);

    @PutMapping("/{id}/{status}")
    OrganizationDto activeOrganization(@PathVariable String id, @PathVariable int status);

    @GetMapping("/checkOrgCode")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void checkOrgCode(@RequestParam String orgCode);
}
