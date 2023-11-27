package com.kary.hahaha3.exceptions.expired;

/**
 * @author:123
 */
public abstract class ExpireException extends Exception{
    public ExpireException() {
    }

    public ExpireException(String message) {
        super(message);
    }

    public ExpireException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpireException(Throwable cause) {
        super(cause);
    }

    public ExpireException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
