package com.job_connect.controller.handler;

import com.job_connect.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ErrorResponse handleException(Exception e) {
        return ErrorResponse.builder()
                .message(e.getMessage())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
    }

}
