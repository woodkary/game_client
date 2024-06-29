package com.kary.hahaha3.exceptions.emptyInput;

/**
 * @author:123
 */
public class VerificationCodeEmptyException extends EmptyInputException{
    public VerificationCodeEmptyException() {
    }

    public VerificationCodeEmptyException(String message) {
        super(message);
    }

    public VerificationCodeEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerificationCodeEmptyException(Throwable cause) {
        super(cause);
    }

    public VerificationCodeEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
