package com.warya.base.sms.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "cache")
public class CacheProperties {
    private Map<String, CacheSpec> specs = new HashMap<>();
    
    @Data
    public static class CacheSpec {
        private long maximumSize = 10000;
        private long expireAfterWriteMinutes = 5;
        private long expireAfterAccessMinutes = 0;
        private boolean recordStats = true;
    }
}

