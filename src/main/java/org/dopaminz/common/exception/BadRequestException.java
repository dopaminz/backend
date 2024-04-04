package org.dopaminz.common.exception;

public class BadRequestException extends DopaminzException {

    public BadRequestException(String message) {
        super(message, 400);
    }
}
