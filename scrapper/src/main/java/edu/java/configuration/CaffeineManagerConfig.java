package edu.java.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableCaching
public class CaffeineManagerConfig {
    private final CacheConfig cacheConfig;

    @Autowired
    public CaffeineManagerConfig(CacheConfig cacheConfig) {
        this.cacheConfig = cacheConfig;
    }

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        Caffeine<Object, Object> caffeine = Caffeine
            .newBuilder()
            .maximumSize(cacheConfig.maximumSize())
            .expireAfterWrite(cacheConfig.expireAfterWrite())
            .expireAfterAccess(cacheConfig.expireAfterAccess())
            .evictionListener(
                (key, value, removalCause) -> log.info(
                    "Eviction of key " + key + " with value " + value + " due to " + removalCause)
            );

        return caffeine;
    }

    @Bean
    public CacheManager caffeineCacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }
}
