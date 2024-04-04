package org.dopaminz.common.exception;

public class ForbiddenException extends DopaminzException {

    public ForbiddenException(String message) {
        super(message, 403);
    }
}
