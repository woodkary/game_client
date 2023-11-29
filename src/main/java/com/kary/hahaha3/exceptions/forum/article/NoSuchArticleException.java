package com.kary.hahaha3.exceptions.forum.article;

import com.kary.hahaha3.exceptions.forum.ForumException;

/**
 * @author:123
 */
public class NoSuchArticleException extends ForumException {
    public NoSuchArticleException() {
    }

    public NoSuchArticleException(String message) {
        super(message);
    }

    public NoSuchArticleException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchArticleException(Throwable cause) {
        super(cause);
    }

    public NoSuchArticleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
