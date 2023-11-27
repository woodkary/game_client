package com.kary.hahaha3.exceptions.connection;

/**
 * @author:123
 */
public class DatabaseConnectionException extends ConnectionException{
    public DatabaseConnectionException() {
    }

    public DatabaseConnectionException(String message) {
        super(message);
    }

    public DatabaseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseConnectionException(Throwable cause) {
        super(cause);
    }

    public DatabaseConnectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
