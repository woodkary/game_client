package com.kary.hahaha3.exceptions.errorInput;

/**
 * @author:123
 */
public class MatchTypeErrorException extends ErrorInputException{
    public MatchTypeErrorException() {
    }

    public MatchTypeErrorException(String message) {
        super(message);
    }

    public MatchTypeErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public MatchTypeErrorException(Throwable cause) {
        super(cause);
    }

    public MatchTypeErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
