package org.dopaminz.common;


import jakarta.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dopaminz.common.exception.DopaminzException;
import org.dopaminz.common.response.CommonResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DopaminzException.class)
    ResponseEntity<CommonResponse<String>> handleException(HttpServletRequest request, DopaminzException e) {
        log.info("잘못된 요청이 들어왔습니다. uri: {} {},  내용: {}",
                request.getMethod(), request.getRequestURI(), e.getMessage());
        requestLogging(request);
        return ResponseEntity.status(e.getCode())
                .body(new CommonResponse<>(
                        String.valueOf(e.getCode()),
                        null,
                        e.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<CommonResponse<String>> handleException(HttpServletRequest request, Exception e) {
        log.error("예상하지 못한 예외가 발생했습니다. uri: {} {}, ", request.getMethod(), request.getRequestURI(), e);
        requestLogging(request);
        return ResponseEntity.internalServerError()
                .body(new CommonResponse<>(
                        "500",
                        null,
                        e.getMessage()
                ));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception e,
            Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest webRequest
    ) {
        HttpServletRequest request = ((ServletWebRequest) webRequest).getRequest();
        log.error("예외가 발생했습니다. uri: {} {}, ", request.getMethod(), request.getRequestURI(), e);
        requestLogging(request);
        return ResponseEntity.status(statusCode)
                .body(new CommonResponse(
                        String.valueOf(statusCode.value()),
                        null,
                        e.getMessage())
                );
    }

    private void requestLogging(HttpServletRequest request) {
        log.info("request header: {}", getHeaders(request));
        log.info("request query string: {}", getQueryString(request));
        log.info("request body: {}", getRequestBody(request));
    }

    private Map<String, Object> getHeaders(HttpServletRequest request) {
        Map<String, Object> headerMap = new HashMap<>();
        Enumeration<String> headerArray = request.getHeaderNames();
        while (headerArray.hasMoreElements()) {
            String headerName = headerArray.nextElement();
            headerMap.put(headerName, request.getHeader(headerName));
        }
        return headerMap;
    }

    private String getQueryString(HttpServletRequest httpRequest) {
        String queryString = httpRequest.getQueryString();
        if (queryString == null) {
            return " - ";
        }
        return queryString;
    }

    private String getRequestBody(HttpServletRequest request) {
        var wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper == null) {
            return " - ";
        }
        try {
            // body 가 읽히지 않고 예외처리 되는 경우에 캐시하기 위함
            wrapper.getInputStream().readAllBytes();
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length == 0) {
                return " - ";
            }
            return new String(buf, wrapper.getCharacterEncoding());
        } catch (Exception e) {
            return " - ";
        }
    }
}
