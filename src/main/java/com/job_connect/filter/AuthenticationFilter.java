package com.job_connect.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.job_connect.config.security.AdminAuthentication;
import com.job_connect.entity.Admin;
import com.job_connect.exception.UnAuthorizeException;
import com.job_connect.repository.AdminRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationFilter extends AbstractFilter{
    public static final String AUTHORIZATION_KEY = "Authorization";
    public static final String AUTHORIZATION_TYPE_KEY = "Bearer ";

    public static final String EMAIL_KEY = "email";
    private final AdminRepository adminRepository;

    public AuthenticationFilter(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            String authValue = request.getHeader(AUTHORIZATION_KEY);
            if (StringUtils.isBlank(authValue) || !authValue.startsWith(AUTHORIZATION_TYPE_KEY)) {
                throw new UnAuthorizeException();
            }
            String token = authValue.substring(7);
            if (StringUtils.isBlank(token)) {
                throw new UnAuthorizeException();
            }
            DecodedJWT jwt = JWT.decode(token);
            String email = jwt.getClaim(EMAIL_KEY).asString();
            if (StringUtils.isBlank(email)) {
                throw new UnAuthorizeException();
            }
            Admin admin = adminRepository.findByEmail(email)
                    .orElseThrow(UnAuthorizeException::new);
            AdminAuthentication authentication = new AdminAuthentication(admin);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw new UnAuthorizeException();
        }
    }

}
