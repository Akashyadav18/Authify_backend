package com.Security.Authify.io;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@Builder
public class PaginatedResponse<T> {

    private List<T> data;
    private int totalPages;
    private long totalElements;
    private int currentPage;
    private int pageSize;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean hasNext;
    private boolean hasPrevious;

    public static <T> PaginatedResponse<T> of(List<T> data, Page<?> page){
        return PaginatedResponse.<T>builder()
                .data(data)
                .currentPage(page.getNumber() + 1)
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .isFirstPage(page.isFirst())
                .isLastPage(page.isLast())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .build();
    }
}
