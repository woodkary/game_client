package com.kary.hahaha3.exceptions.forum.comment;

import com.kary.hahaha3.exceptions.forum.ForumException;

/**
 * @author:123
 */
public class NoSuchCommentException extends ForumException {
    public NoSuchCommentException() {
    }

    public NoSuchCommentException(String message) {
        super(message);
    }

    public NoSuchCommentException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchCommentException(Throwable cause) {
        super(cause);
    }

    public NoSuchCommentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
