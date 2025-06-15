package com.job_connect.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorResponse {
    private int code;
    private String message;
    private List<ErrorDetail> detail;
}
