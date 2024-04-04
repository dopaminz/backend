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
                "요청 성공"
        );
    }

    public static <T> CommonResponse<T> created(T data) {
        return new CommonResponse<>(
                "201",
                data,
                "요청 성공"
        );
    }
}
