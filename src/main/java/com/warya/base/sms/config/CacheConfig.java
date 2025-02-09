package com.warya.base.sms.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Scheduler;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.warya.base.sms.properties.CacheProperties;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.cache.CaffeineCacheMetrics;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Log4j2
@Configuration
@EnableCaching
@EnableScheduling
@RequiredArgsConstructor
public class CacheConfig {

    private final MeterRegistry meterRegistry;
    private final CacheProperties cacheProperties;

    public static class CacheNames {
        public static final String VERIFICATION_CODES = "verificationCodes";
        public static final String SMS_RATE_LIMIT = "smsRateLimit";
        public static final String USER_PREFERENCES = "userPreferences";

        private CacheNames() {} // Prevent instantiation
    }

    /**
     * Creates and configures the CacheManager with all required caches
     */
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        registerCache(cacheManager, CacheNames.VERIFICATION_CODES,
            buildVerificationCodesCache());
        registerCache(cacheManager, CacheNames.SMS_RATE_LIMIT,
            buildRateLimitCache());
        registerCache(cacheManager, CacheNames.USER_PREFERENCES,
            buildUserPreferencesCache());

        cacheManager.setCaffeine(Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .recordStats());

        return cacheManager;
    }

    /**
     * Builds the verification codes cache with specific requirements
     */
    private CaffeineCache buildVerificationCodesCache() {
        CacheProperties.CacheSpec spec = cacheProperties.getSpecs()
            .getOrDefault(CacheNames.VERIFICATION_CODES, new CacheProperties.CacheSpec());

        com.github.benmanes.caffeine.cache.Cache<Object, Object> cache = Caffeine.newBuilder()
            .maximumSize(spec.getMaximumSize())
            .expireAfterWrite(spec.getExpireAfterWriteMinutes(), TimeUnit.MINUTES)
            .scheduler(Scheduler.systemScheduler()) // For background maintenance
            .recordStats()
            .removalListener((key, value, cause) ->
                log.debug("Verification code removed: key={}, cause={}", key, cause))
            .build();

        // Register cache metrics
        CaffeineCacheMetrics.monitor(meterRegistry, cache, CacheNames.VERIFICATION_CODES);

        return new CaffeineCache(CacheNames.VERIFICATION_CODES, cache);
    }

    /**
     * Builds the rate limit cache with specific requirements
     */
    private CaffeineCache buildRateLimitCache() {
        CacheProperties.CacheSpec spec = cacheProperties.getSpecs()
            .getOrDefault(CacheNames.SMS_RATE_LIMIT, new CacheProperties.CacheSpec());

        com.github.benmanes.caffeine.cache.Cache<Object, Object> cache = Caffeine.newBuilder()
            .maximumSize(spec.getMaximumSize())
            .expireAfterWrite(24, TimeUnit.HOURS) // Rate limits reset daily
            .scheduler(Scheduler.systemScheduler())
            .recordStats()
            .removalListener((key, value, cause) ->
                log.debug("Rate limit entry removed: key={}, cause={}", key, cause))
            .build();

        CaffeineCacheMetrics.monitor(meterRegistry, cache, CacheNames.SMS_RATE_LIMIT);

        return new CaffeineCache(CacheNames.SMS_RATE_LIMIT, cache);
    }

    /**
     * Builds the user preferences cache with specific requirements
     */
    private CaffeineCache buildUserPreferencesCache() {
        CacheProperties.CacheSpec spec = cacheProperties.getSpecs()
            .getOrDefault(CacheNames.USER_PREFERENCES, new CacheProperties.CacheSpec());

        com.github.benmanes.caffeine.cache.Cache<Object, Object> cache = Caffeine.newBuilder()
            .maximumSize(spec.getMaximumSize())
            .expireAfterAccess(spec.getExpireAfterAccessMinutes(), TimeUnit.MINUTES)
            .scheduler(Scheduler.systemScheduler())
            .recordStats()
            .removalListener((key, value, cause) ->
                log.debug("User preferences removed: key={}, cause={}", key, cause))
            .build();

        CaffeineCacheMetrics.monitor(meterRegistry, cache, CacheNames.USER_PREFERENCES);

        return new CaffeineCache(CacheNames.USER_PREFERENCES, cache);
    }

    /**
     * Registers a cache with the CacheManager
     */
    private void registerCache(CaffeineCacheManager cacheManager, String name, CaffeineCache cache) {
        cacheManager.registerCustomCache(name, cache.getNativeCache());
    }

    /**
     * Custom key generator for cache keys
     * Generates unique keys based on method signature and parameters
     */
    @Bean
    public KeyGenerator customKeyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getSimpleName())
              .append(":")
              .append(method.getName())
              .append(":");

            if (params.length > 0) {
                Arrays.stream(params)
                    .forEach(param -> sb.append(param.toString()).append(":"));
                sb.deleteCharAt(sb.length() - 1); // Remove last colon
            }

            return sb.toString();
        };
    }

    /**
     * Scheduled task to report cache statistics
     * Runs every 5 minutes
     */
    @Scheduled(fixedRate = 300000) // 5 minutes
    public void reportCacheStatistics() {
        CaffeineCacheManager manager = (CaffeineCacheManager) cacheManager();

        Arrays.asList(
            CacheNames.VERIFICATION_CODES,
            CacheNames.SMS_RATE_LIMIT,
            CacheNames.USER_PREFERENCES
        ).forEach(cacheName -> {
            CaffeineCache cache = (CaffeineCache) manager.getCache(cacheName);
            if (cache != null) {
                com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache =
                    (com.github.benmanes.caffeine.cache.Cache<Object, Object>) cache.getNativeCache();

                CacheStats stats = nativeCache.stats();
                log.info("Cache '{}' statistics:\n" +
                        "  Hits: {}\n" +
                        "  Misses: {}\n" +
                        "  Hit Rate: {}\n" +
                        "  Miss Rate: {}\n" +
                        "  Evictions: {}\n" +
                        "  Eviction Weight: {}\n" +
                        "  Current Size: {}",
                    cacheName,
                    stats.hitCount(),
                    stats.missCount(),
                    String.format("%.2f%%", stats.hitRate() * 100),
                    String.format("%.2f%%", stats.missRate() * 100),
                    stats.evictionCount(),
                    stats.evictionWeight(),
                    nativeCache.estimatedSize());
            }
        });
    }
}
