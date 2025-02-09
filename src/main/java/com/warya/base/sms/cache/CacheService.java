package com.warya.base.sms.cache;

import com.warya.base.sms.exception.SmsServiceException;
import com.warya.base.sms.properties.SmsProperties;
import com.warya.base.sms.util.PhoneNumberUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Log4j2
@RequiredArgsConstructor
public class CacheService {
    private static final String VERIFICATION_CACHE = "verificationCodes";
    private static final String RATE_LIMIT_CACHE = "smsRateLimit";
    private final CacheManager cacheManager;
    private final SmsProperties smsProperties;
    private final Random random = new Random();

    @Cacheable(value = "verificationCodes", key = "#phoneNumber")
    public String generateAndCacheCode(String phoneNumber) {
        String code = generateRandomCode();
        log.debug("Generated verification code for: {}", PhoneNumberUtil.maskPhoneNumber(phoneNumber));
        return code;
    }

    @Cacheable(value = "smsRateLimit", key = "#phoneNumber", unless = "#result >= 5")
    public int incrementSmsCount(String phoneNumber) {
        Cache cache = cacheManager.getCache(RATE_LIMIT_CACHE);
        if (cache == null) {
            throw new SmsServiceException("Rate limit cache not available");
        }

        Integer count = cache.get(phoneNumber, Integer.class);
        return (count == null) ? 1 : count + 1;
    }

    private String generateRandomCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < smsProperties.getCodeLength(); i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

    public Cache getVerificationCache() {
        return cacheManager.getCache(VERIFICATION_CACHE);
    }

    public Cache getRateLimitCache() {
        return cacheManager.getCache(RATE_LIMIT_CACHE);
    }
}