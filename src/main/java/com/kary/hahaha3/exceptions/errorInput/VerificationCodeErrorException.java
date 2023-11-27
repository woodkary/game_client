package com.kary.hahaha3.exceptions.errorInput;

/**
 * @author:123
 */
public class VerificationCodeErrorException extends ErrorInputException {
    public VerificationCodeErrorException() {
    }

    public VerificationCodeErrorException(String message) {
        super(message);
    }

    public VerificationCodeErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerificationCodeErrorException(Throwable cause) {
        super(cause);
    }

    public VerificationCodeErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
