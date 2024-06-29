package com.kary.hahaha3.exceptions.emptyInput;

/**
 * @author:123
 */
public class UsernameEmptyException extends EmptyInputException {
    public UsernameEmptyException() {
        super();
    }

    public UsernameEmptyException(String message) {
        super(message);
    }

    public UsernameEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameEmptyException(Throwable cause) {
        super(cause);
    }

    protected UsernameEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
