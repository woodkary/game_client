package com.kary.hahaha3.exceptions.emptyInput;

/**
 * @author:123
 */
public class PasswordEmptyException extends EmptyInputException{
    public PasswordEmptyException() {
        super();
    }

    public PasswordEmptyException(String message) {
        super(message);
    }

    public PasswordEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordEmptyException(Throwable cause) {
        super(cause);
    }

    protected PasswordEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
