package com.job_connect.filter;

import com.job_connect.exception.BusinessException;
import com.job_connect.model.ErrorResponse;
import com.job_connect.util.ObjectMapperUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public abstract class AbstractFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            doFilter(request, response, filterChain);
        }
        catch (BusinessException e) {
            ErrorResponse error = ErrorResponse.builder()
                    .code(e.getCode())
                    .message(e.getMessage())
                    .build();

            response.setStatus(e.getCode());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getOutputStream().write(ObjectMapperUtil.writeValueAsBytes(error));
        }
        catch (Exception e ){
            ErrorResponse error = ErrorResponse.builder()
                    .code(500)
                    .message(e.getMessage())
                    .build();

            response.setStatus(500);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getOutputStream().write(ObjectMapperUtil.writeValueAsBytes(error));
        }

    }

    public abstract void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException;

}
