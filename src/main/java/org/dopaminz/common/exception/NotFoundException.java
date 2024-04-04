package org.dopaminz.common.exception;

public class NotFoundException extends DopaminzException {

    public NotFoundException(String message) {
        super(message, 400);
    }
}
