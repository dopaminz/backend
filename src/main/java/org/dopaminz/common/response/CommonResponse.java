package org.dopaminz.common.response;

public record CommonResponse<T>(
        String code,
        T data,
        String msg
) {
    public static <T> CommonResponse<T> ok(T data) {
        return new CommonResponse<>(
                "200",
                data,
                ""
        );
    }
}
