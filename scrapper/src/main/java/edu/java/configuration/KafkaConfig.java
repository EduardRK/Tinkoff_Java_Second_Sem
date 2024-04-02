package edu.java.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "kafka", ignoreUnknownFields = false)
public record KafkaConfig(
    @NotNull
    String bootstrapServer,
    @NotNull
    String groupId,
    @NotNull
    String autoOffsetReset,
    int lingerMs,
    @NotNull
    String topicName

) {
}
