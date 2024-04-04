package org.dopaminz.common.exception;

public class UnauthorizedException extends DopaminzException {

    public UnauthorizedException(String message) {
        super(message, 401);
    }
}
