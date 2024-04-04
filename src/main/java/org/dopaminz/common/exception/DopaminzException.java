package org.dopaminz.common.exception;

import lombok.Getter;

@Getter
public class DopaminzException extends RuntimeException {

    private final int code;

    public DopaminzException(String message, int code) {
        super(message);
        this.code = code;
    }
}
