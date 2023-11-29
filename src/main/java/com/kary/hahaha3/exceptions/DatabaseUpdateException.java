package com.kary.hahaha3.exceptions;

/**
 * @author:123
 */
public class DatabaseUpdateException extends Exception{
    public DatabaseUpdateException() {
    }

    public DatabaseUpdateException(String message) {
        super(message);
    }

    public DatabaseUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseUpdateException(Throwable cause) {
        super(cause);
    }

    public DatabaseUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
