package com.warya.base.sms.service;

import com.warya.base.sms.cache.CacheService;
import com.warya.base.sms.exception.InvalidPhoneNumberException;
import com.warya.base.sms.exception.SmsServiceException;
import com.warya.base.sms.model.SmsRequest;
import com.warya.base.sms.model.SmsResponse;
import com.warya.base.sms.model.VerificationCode;
import com.warya.base.sms.properties.SmsProperties;
import com.warya.base.sms.util.PhoneNumberUtil;
import com.warya.base.sms.util.XmlUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.Cache;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Service
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {
    private static final int MAX_SMS_PER_DAY = 5;

    private final SmsProperties smsProperties;
    private final RestTemplate restTemplate;
    private final PhoneNumberUtil phoneNumberUtil;
    private final XmlUtil xmlUtil;
    private final CacheService cacheService;

    @Override
    public SmsResponse sendVerificationCode(String phoneNumber) {
        log.info("Starting verification code send process for: {}", PhoneNumberUtil.maskPhoneNumber(phoneNumber));

        validatePhoneNumber(phoneNumber);

        checkRateLimit(phoneNumber);

        try {
            String code = cacheService.generateAndCacheCode(phoneNumber);

            SmsResponse response = sendSms(phoneNumber, formatMessage(code));

            if (response.isSuccess()) {
                cacheService.incrementSmsCount(phoneNumber);
            }

            log.info("Verification code sent successfully to: {}", PhoneNumberUtil.maskPhoneNumber(phoneNumber));
            return response;

        } catch (Exception e) {
            log.error("Failed to send verification code to: {}", PhoneNumberUtil.maskPhoneNumber(phoneNumber), e);
            throw new SmsServiceException("Failed to send verification code", e);
        }
    }

    @Override
    public boolean verifyCode(String phoneNumber, String code) {
        log.info("Verifying code for phone number: {}", PhoneNumberUtil.maskPhoneNumber(phoneNumber));

        validatePhoneNumber(phoneNumber);

        Cache cache = cacheService.getVerificationCache();

        if (cache == null) {
            throw new SmsServiceException("Verification cache not available");
        }

        VerificationCode cachedCode = cache.get(phoneNumber, VerificationCode.class);

        if (cachedCode == null) {
            log.warn("No verification code found for: {}", PhoneNumberUtil.maskPhoneNumber(phoneNumber));
            return false;
        }

        try {
            if (isExpired(cachedCode.getExpirationTime())) {
                log.info("Verification code expired for: {}", PhoneNumberUtil.maskPhoneNumber(phoneNumber));
                cache.evict(phoneNumber);
                return false;
            }

            boolean isValid = cachedCode.getCode().equals(code);

            if (isValid) {
                log.info("Code verified successfully for: {}", PhoneNumberUtil.maskPhoneNumber(phoneNumber));
                cache.evict(phoneNumber);
            } else {
                log.warn("Invalid code provided for: {}", PhoneNumberUtil.maskPhoneNumber(phoneNumber));
            }

            return isValid;

        } catch (Exception e) {
            log.error("Error during code verification for: {}", PhoneNumberUtil.maskPhoneNumber(phoneNumber), e);
            throw new SmsServiceException("Error verifying code", e);
        }
    }

    private String formatMessage(String code) {
        return String.format("Your verification code is: %s. Valid for %d minutes.",
                code, smsProperties.getCodeExpirationMinutes());
    }

    private SmsResponse sendSms(String phoneNumber, String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        SmsRequest request = SmsRequest.builder()
                .login(smsProperties.getLogin())
                .password(smsProperties.getPassword())
                .oadc(smsProperties.getSender())
                .msisdn_to(phoneNumber)
                .body(message)
                .build();

        HttpEntity<SmsRequest> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    smsProperties.getApiUrl(),
                    entity,
                    String.class
            );

            return xmlUtil.parseXmlResponse(response.getBody());
        } catch (Exception e) {
            throw new SmsServiceException("Failed to send SMS", e);
        }
    }

    private void checkRateLimit(String phoneNumber) {
        Cache cache = cacheService.getRateLimitCache();

        if (cache == null) {
            throw new SmsServiceException("Rate limit cache not available");
        }

        Integer count = cache.get(phoneNumber, Integer.class);

        if (count != null && count >= MAX_SMS_PER_DAY) {
            log.warn("Rate limit exceeded for: {}", PhoneNumberUtil.maskPhoneNumber(phoneNumber));
            throw new SmsServiceException("Daily SMS limit exceeded");
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (!phoneNumberUtil.isValid(phoneNumber)) {
            log.error("Invalid phone number format: {}", PhoneNumberUtil.maskPhoneNumber(phoneNumber));
            throw new InvalidPhoneNumberException("Invalid phone number format");
        }
    }

    private boolean isExpired(long timestamp) {
        return System.currentTimeMillis() > timestamp;
    }
}