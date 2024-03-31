package edu.java.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BucketRateLimitingConfig {
    private final RateLimitingConfig rateLimitingConfig;

    @Autowired
    public BucketRateLimitingConfig(RateLimitingConfig rateLimitingConfig) {
        this.rateLimitingConfig = rateLimitingConfig;
    }
}
