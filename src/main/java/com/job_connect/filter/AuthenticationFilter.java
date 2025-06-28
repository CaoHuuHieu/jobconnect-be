package com.job_connect.filter;

import com.job_connect.config.security.TokenAuthentication;
import com.job_connect.entity.Admin;
import com.job_connect.exception.BusinessException;
import com.job_connect.repository.AdminRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends AbstractFilter{

    public static final String AUTHORIZATION_KEY = "Authorization";
    public static final String AUTHORIZATION_TYPE_KEY = "Bearer ";

    public static final String EMAIL_KEY = "email";

    private final AdminRepository adminRepository;
    private final JwtDecoder jwtDecoder;

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String authValue = request.getHeader(AUTHORIZATION_KEY);
        if (StringUtils.isBlank(authValue) || !authValue.startsWith(AUTHORIZATION_TYPE_KEY)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authValue.substring(7);
        Jwt jwt = jwtDecoder.decode(token);
        String email = jwt.getClaim(EMAIL_KEY).toString();
        if (StringUtils.isBlank(email)) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() ->  new BusinessException(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase()));
        TokenAuthentication authentication = new TokenAuthentication(admin);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

}
