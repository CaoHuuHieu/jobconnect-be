package com.job_connect.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.function.Function;

@Data
@Builder
public class PageResponse<T> {
    private List<T> data;
    private int page;
    private long totalElement;
    private int totalPage;

    public static<T,E> PageResponse<T> toPageResponse(Page<E> page, Function<E, T> converter) {
        return PageResponse.<T>builder()
                .data(page.stream().map(converter).toList())
                .page(page.getNumber())
                .totalElement(page.getTotalElements())
                .totalPage(page.getTotalPages())
                .build();
    }
}
