package com.warya.base.common;

import com.warya.base.common.exception.BusinessException;
import com.warya.base.common.response.AppErrorResponse;
import com.warya.base.common.response.FieldValidationError;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;
    private static final String DEFAULT_ERROR_CODE = "ERR_INTERNAL";

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<AppErrorResponse> handleBusinessException(BusinessException ex) {
        String message = getMessage(ex.getCode(), ex.getArgs());

        AppErrorResponse response = AppErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .error(message)
                .status(ex.getStatus().value())
                .statusText(ex.getStatus().getReasonPhrase())
                .build();

        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AppErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<FieldValidationError> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> FieldValidationError.builder()
                        .field(error.getField())
                        .message(error.getDefaultMessage())
                        .build())
                .toList();

        AppErrorResponse response = AppErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .error("Validation failed")
                .status(status.value())
                .statusText(status.getReasonPhrase())
                .validationErrors(fieldErrors)
                .build();

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<AppErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        String message = getMessage("AUTH_USERNAME_PASSWORD_INVALID");

        AppErrorResponse response = AppErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .error(message)
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<AppErrorResponse> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        String message = getMessage("AUTH_ACCESS_DENIED");

        AppErrorResponse response = AppErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .error(message)
                .build();

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppErrorResponse> handleGenericException(Exception ex) {
        String message = getMessage(DEFAULT_ERROR_CODE);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        AppErrorResponse response = AppErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .error(message)
                .status(status.value())
                .statusText(status.getReasonPhrase())
                .build();

        return new ResponseEntity<>(response, status);
    }

    private String getMessage(String code, Object... args) {
        return Optional.ofNullable(code)
                .map(c -> {
                    try {
                        return messageSource.getMessage(c, args, LocaleContextHolder.getLocale());
                    } catch (Exception e) {
                        return messageSource.getMessage(DEFAULT_ERROR_CODE, null, LocaleContextHolder.getLocale());
                    }
                })
                .orElse(messageSource.getMessage(DEFAULT_ERROR_CODE, null, LocaleContextHolder.getLocale()));
    }
}