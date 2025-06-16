package com.job_connect.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDetail {
    private String issue;
    private String field;
}
