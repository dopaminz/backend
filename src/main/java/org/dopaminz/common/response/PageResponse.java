package org.dopaminz.common.response;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponse<T>(
        Boolean hasNext,
        List<T> contents
) {
    public static <T> PageResponse<T> from(Page<T> result) {
        return new PageResponse<>(
                result.hasNext(),
                result.getContent()
        );
    }
}
