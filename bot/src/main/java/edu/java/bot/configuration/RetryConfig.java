package edu.java.bot.configuration;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "retry", ignoreUnknownFields = false)
public record RetryConfig(
    @NotNull
    String type,
    @NotNull
    Duration baseDelay,
    long maxAttempts,
    List<Integer> statusCodes
) {
}
