package com.kary.hahaha3.exceptions.emptyInput;

/**
 * @author:123
 */
public class EmailEmptyException extends EmptyInputException{
    public EmailEmptyException() {
    }

    public EmailEmptyException(String message) {
        super(message);
    }

    public EmailEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailEmptyException(Throwable cause) {
        super(cause);
    }

    public EmailEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
