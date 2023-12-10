package com.kary.hahaha3.exceptions.errorInput;

/**
 * @author:123
 */
public class GameNotFoundException extends ErrorInputException{
    public GameNotFoundException() {
    }

    public GameNotFoundException(String message) {
        super(message);
    }

    public GameNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameNotFoundException(Throwable cause) {
        super(cause);
    }

    public GameNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
