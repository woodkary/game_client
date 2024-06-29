package com.kary.hahaha3.exceptions.forum;

/**
 * @author:123
 */
public abstract class ForumException extends Exception{
    public ForumException() {
    }

    public ForumException(String message) {
        super(message);
    }

    public ForumException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForumException(Throwable cause) {
        super(cause);
    }

    public ForumException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
