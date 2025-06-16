package com.job_connect.helper;

import com.job_connect.entity.Admin;
import com.job_connect.entity.Organization;
import com.job_connect.entity.Role;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthHelper {

    public static Admin getCurrentAdmin() {
//        SecurityContextHolder.getContext().getAuthentication()
        return Admin.builder()
                .role(Role.builder().code(Role.SUPER_ADMIN).build())
                .organization(Organization.builder().id("1").build())
                .build();
    }

    public static String getCurrentRole() {
//        SecurityContextHolder.getContext().getAuthentication()
        Admin admin = getCurrentAdmin();
        return admin.getRole().getCode();
    }

    public static String getCurrentOrg() {
//        SecurityContextHolder.getContext().getAuthentication()
        Admin admin = getCurrentAdmin();
        return admin.getOrganization().getId();
    }
}
