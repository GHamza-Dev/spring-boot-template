package com.warya.base.sms.exception;

public class SmsServiceException extends RuntimeException {
    public SmsServiceException(String message) {
        super(message);
    }

    public SmsServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}