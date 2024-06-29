package com.kary.hahaha3.exceptions.expired;

/**
 * @author:123
 */
public class VerificationCodeExpireException extends ExpireException{
    public VerificationCodeExpireException() {
    }

    public VerificationCodeExpireException(String message) {
        super(message);
    }

    public VerificationCodeExpireException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerificationCodeExpireException(Throwable cause) {
        super(cause);
    }

    public VerificationCodeExpireException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
