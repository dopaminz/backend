package org.dopaminz.common.exception;

public class InternalServerErrorException extends DopaminzException {

    public InternalServerErrorException(String message) {
        super(message, 500);
    }
}
