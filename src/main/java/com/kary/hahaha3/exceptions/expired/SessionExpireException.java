package com.kary.hahaha3.exceptions.expired;

/**
 * @author:123
 */
public class SessionExpireException extends ExpireException{
    public SessionExpireException() {
        super();
    }

    public SessionExpireException(String message) {
        super(message);
    }

    public SessionExpireException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionExpireException(Throwable cause) {
        super(cause);
    }

    public SessionExpireException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
