package com.job_connect.exception;

import com.job_connect.model.ErrorDetail;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class BusinessException extends RuntimeException{

    private int code;
    private List<ErrorDetail> errors;

    public BusinessException(int code, String message) {
        this(code, message, null);
    }

    public BusinessException(HttpStatus httpStatus) {
        this(httpStatus.value(), httpStatus.getReasonPhrase());
    }


    public BusinessException(int code, String message, List<ErrorDetail> errors) {
        super(message);
        this.code = code;
        this.errors = errors;
    }

}
