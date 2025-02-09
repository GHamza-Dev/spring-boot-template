package com.warya.base.sms.exception;

public class CodeVerificationException extends RuntimeException {
    public CodeVerificationException(String message) {
        super(message);
    }
}