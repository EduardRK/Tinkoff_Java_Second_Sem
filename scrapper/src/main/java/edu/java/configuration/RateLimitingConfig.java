package edu.java.configuration;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "rate-limiting", ignoreUnknownFields = false)
public record RateLimitingConfig(
    long capacity,
    long refillTokens,
    @NotNull
    Duration refillInterval
) {
}
