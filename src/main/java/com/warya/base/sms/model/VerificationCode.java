package com.warya.base.sms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationCode {
    private String code;
    private String phoneNumber;
    private long expirationTime;
    
    public boolean isExpired() {
        return System.currentTimeMillis() > expirationTime;
    }
}