package com.job_connect.helper;

import com.job_connect.config.security.TokenAuthentication;
import com.job_connect.entity.Admin;
import com.job_connect.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthHelper {

    private AuthHelper() {}

    public static Admin getCurrentAdmin() {
          TokenAuthentication authentication = (TokenAuthentication) SecurityContextHolder
                  .getContext()
                  .getAuthentication();
          if(authentication == null)
              throw new BusinessException(HttpStatus.UNAUTHORIZED);
          return authentication.admin();
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
