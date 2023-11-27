package com.kary.hahaha3.exceptions.connection;

/**
 * @author:123
 */
public class VerificationCodeSendingException extends ConnectionException{
    public VerificationCodeSendingException() {
    }

    public VerificationCodeSendingException(String message) {
        super(message);
    }

    public VerificationCodeSendingException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerificationCodeSendingException(Throwable cause) {
        super(cause);
    }

    public VerificationCodeSendingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
