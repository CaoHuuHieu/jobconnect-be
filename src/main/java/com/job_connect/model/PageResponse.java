package com.job_connect.model;

import lombok.Data;

import java.util.List;

@Data
public class PageResponse<T> {
    private List<T> data;
    private int page;
    private int totalElement;
    private int totalPage;
}
