package com.job_connect.model;

import lombok.Data;

@Data
public class PageRequest {
    private int page = 0;
    private int size = Integer.MAX_VALUE;
    private String sort = "DESC";
    private String orderBy = "createdAt";
    private String q;
    private String search;
}
