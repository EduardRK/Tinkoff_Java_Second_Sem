package edu.java.configuration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "cache", ignoreUnknownFields = false)
public record CacheConfig(
    int maximumSize,
    @NotNull
    Duration expireAfterWrite,
    @NotNull
    Duration expireAfterAccess,
    @NotNull
    Duration refreshAfterWrite,
    @NotBlank
    String evictionStrategy
) {
}
