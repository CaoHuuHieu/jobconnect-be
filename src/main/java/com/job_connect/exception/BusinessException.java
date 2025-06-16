package com.job_connect.exception;

import com.job_connect.model.ErrorDetail;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BusinessException extends RuntimeException{
    private int code;
    private String message;
    private List<ErrorDetail> errors;
}
