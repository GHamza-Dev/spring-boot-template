package com.warya.base.sms.service;

import com.warya.base.sms.model.SmsResponse;

public interface SmsService {
    SmsResponse sendVerificationCode(String phoneNumber);
    boolean verifyCode(String phoneNumber, String code);
}