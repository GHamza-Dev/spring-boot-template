package com.warya.base.sms.controller;

import com.warya.base.sms.exception.CodeVerificationException;
import com.warya.base.sms.exception.InvalidPhoneNumberException;
import com.warya.base.sms.exception.SmsServiceException;
import com.warya.base.sms.model.SmsResponse;
import com.warya.base.sms.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/verification")
@RequiredArgsConstructor
public class SmsVerificationController {
    private final SmsService smsService;

    @PostMapping("/send")
    public ResponseEntity<SmsResponse> sendVerificationCode(@RequestParam String phoneNumber) {
        log.info("Received request to send verification code to phone number: {}", phoneNumber);
        SmsResponse response = smsService.sendVerificationCode(phoneNumber);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyCode(
        @RequestParam String phoneNumber,
        @RequestParam String code
    ) {
        log.info("Received request to verify code for phone number: {}", phoneNumber);
        boolean isValid = smsService.verifyCode(phoneNumber, code);
        return ResponseEntity.ok(isValid);
    }

    @ExceptionHandler(InvalidPhoneNumberException.class)
    public ResponseEntity<String> handleInvalidPhoneNumber(InvalidPhoneNumberException e) {
        log.error("Invalid phone number error", e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(SmsServiceException.class)
    public ResponseEntity<String> handleSmsServiceError(SmsServiceException e) {
        log.error("SMS service error", e);
        return ResponseEntity.internalServerError().body("Failed to process SMS operation");
    }

    @ExceptionHandler(CodeVerificationException.class)
    public ResponseEntity<String> handleCodeVerificationError(CodeVerificationException e) {
        log.error("Code verification error", e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}