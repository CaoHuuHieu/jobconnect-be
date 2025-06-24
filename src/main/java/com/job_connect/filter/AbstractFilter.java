package com.job_connect.filter;

import com.job_connect.exception.UnAuthorizeException;
import com.job_connect.model.ErrorResponse;
import com.job_connect.util.ObjectMapperUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public abstract class AbstractFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            doFilter(request, response, filterChain);
        }catch (Exception e) {
            if(e instanceof UnAuthorizeException) {
                ErrorResponse error = ErrorResponse.builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .message("UNAUTHORIZED")
                        .build();

                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getOutputStream().write(ObjectMapperUtil.writeValueAsBytes(error));
            }
        }
    }

    public abstract void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain);

}
