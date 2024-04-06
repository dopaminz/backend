package org.dopaminz.common.response;

import java.util.List;
import org.springframework.data.domain.Page;

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
