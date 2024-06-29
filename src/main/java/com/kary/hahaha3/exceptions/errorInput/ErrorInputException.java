package com.kary.hahaha3.exceptions.errorInput;

/**
 * @author:123
 */
public class ErrorInputException extends Exception{
    public ErrorInputException() {
        super();
    }

    public ErrorInputException(String message) {
        super(message);
    }

    public ErrorInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorInputException(Throwable cause) {
        super(cause);
    }

    protected ErrorInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
