package com.kary.hahaha3.exceptions.emptyInput;

/**
 * @author:123
 */
public class EmptyInputException extends Exception{
    public EmptyInputException() {
        super();
    }

    public EmptyInputException(String message) {
        super(message);
    }

    public EmptyInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyInputException(Throwable cause) {
        super(cause);
    }

    protected EmptyInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
