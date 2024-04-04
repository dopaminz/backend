package org.dopaminz.common.exception;

public class ConflictException extends DopaminzException {

    public ConflictException(String message) {
        super(message, 409);
    }
}
