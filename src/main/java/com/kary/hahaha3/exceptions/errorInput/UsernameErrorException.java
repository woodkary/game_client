package com.kary.hahaha3.exceptions.errorInput;

/**
 * @author:123
 */
public class UsernameErrorException extends ErrorInputException {
    public UsernameErrorException() {
        super();
    }

    public UsernameErrorException(String message) {
        super(message);
    }

    public UsernameErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameErrorException(Throwable cause) {
        super(cause);
    }

    protected UsernameErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
