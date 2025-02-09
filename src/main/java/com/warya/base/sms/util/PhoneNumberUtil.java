package com.warya.base.sms.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Log4j2
@Component
public class PhoneNumberUtil {
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[1-9]\\d{1,14}$");

    public boolean isValid(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            log.debug("Phone number is null or empty");
            return false;
        }

        boolean isValid = PHONE_PATTERN.matcher(phoneNumber).matches();
        log.debug("Phone number validation result: {}", isValid);
        return isValid;
    }

    public static String maskPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() < 8) {
            return "INVALID";
        }
        return "XXXXXX" + phoneNumber.substring(phoneNumber.length() - 4);
    }
}