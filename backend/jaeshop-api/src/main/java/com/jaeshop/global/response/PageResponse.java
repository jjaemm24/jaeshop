package com.jaeshop.global.response;

import lombok.Builder;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Builder
public class PageResponse<T> {

    private final List<T> content;
    private final int page;
    private final int size;
    private final int totalPages;
    private final long totalElements;
    private final boolean hasNext;
    private final boolean hasPrev;

    public static <T> PageResponse<T> of(List<T> content, int page, int size, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / (double) size);
        boolean hasPrev = page > 1;
        boolean hasNext = page < totalPages;

        return PageResponse.<T>builder()
                .content(content)
                .page(page)
                .size(size)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .hasPrev(hasPrev)
                .hasNext(hasNext)
                .build();
    }

    public static <T> PageResponse<T> empty(int page, int size) {
        return PageResponse.<T>builder()
                .content(Collections.emptyList())
                .page(page)
                .size(size)
                .totalPages(0)
                .totalElements(0)
                .hasPrev(false)
                .hasNext(false)
                .build();
    }


}
