package com.warya.base.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {
    private final String code;
    private final Object[] args;
    private final HttpStatus status;

    public BusinessException(String code, HttpStatus status) {
        this(code, null, status);
    }

    public BusinessException(String code, Object[] args, HttpStatus status) {
        this.code = code;
        this.args = args;
        this.status = status;
    }
}