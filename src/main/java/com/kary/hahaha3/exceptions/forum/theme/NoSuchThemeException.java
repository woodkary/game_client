package com.kary.hahaha3.exceptions.forum.theme;

import com.kary.hahaha3.exceptions.forum.ForumException;

/**
 * @author:123
 */
public class NoSuchThemeException extends ForumException {
    public NoSuchThemeException() {
    }

    public NoSuchThemeException(String message) {
        super(message);
    }

    public NoSuchThemeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchThemeException(Throwable cause) {
        super(cause);
    }

    public NoSuchThemeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
