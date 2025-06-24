package com.job_connect.helper;

import com.job_connect.config.security.AdminAuthentication;
import com.job_connect.entity.Admin;
import com.job_connect.exception.UnAuthorizeException;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthHelper {

    public static Admin getCurrentAdmin() {
      try {
          AdminAuthentication authentication = (AdminAuthentication) SecurityContextHolder
                  .getContext()
                  .getAuthentication();
          return authentication.getAdmin();
      } catch (Exception e) {
          throw new UnAuthorizeException();
      }
    }

    public static String getCurrentRole() {
        Admin admin = getCurrentAdmin();
        return admin.getRole().getCode();
    }

    public static String getCurrentOrg() {
        Admin admin = getCurrentAdmin();
        return admin.getOrganization().getId();
    }
}
