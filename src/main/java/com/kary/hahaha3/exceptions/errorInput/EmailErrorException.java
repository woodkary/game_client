package com.kary.hahaha3.exceptions.errorInput;

/**
 * @author:123
 */
public class EmailErrorException extends ErrorInputException {
    public EmailErrorException() {
    }

    public EmailErrorException(String message) {
        super(message);
    }

    public EmailErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailErrorException(Throwable cause) {
        super(cause);
    }

    public EmailErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
